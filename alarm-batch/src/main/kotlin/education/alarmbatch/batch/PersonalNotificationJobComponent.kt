package education.alarmbatch.batch

import education.alarmbatch.dto.PersonalNotificationDto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.transaction.PlatformTransactionManager
import java.sql.ResultSet
import java.time.LocalDateTime
import java.util.concurrent.CompletionException
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

@Configuration
class PersonalNotificationJobComponent(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val dataSource: DataSource,
    private val kafkaTemplate: KafkaTemplate<String, PersonalNotificationDto>,
) {
    @Bean
    fun job(): Job =
        JobBuilder("job", jobRepository)
            .incrementer(RunIdIncrementer()) // RunIdIncrementer를 추가하여 매번 다른 JobParameters를 사용
            .start(step())
            .build()

    @Bean
    fun step(): Step =
        StepBuilder("personalNotificationStep2", jobRepository)
            .chunk<PersonalNotificationDto, PersonalNotificationDto>(1000, transactionManager)
            .reader(notificationReader())
            .processor(notificationProcessor())
            .writer(notificationWriter())
            .build()

    @Bean
    fun notificationReader(): JdbcCursorItemReader<PersonalNotificationDto> =
        JdbcCursorItemReaderBuilder<PersonalNotificationDto>()
            .name("personalNotificationReader")
            .dataSource(dataSource)
            .sql(
                "select * from personal_notification p " +
                    "join notification n on n.id = p.id " +
                    "join user_entity u on u.id = p.receiver " +
                    "where n.notification_transfer_time < ? and n.transmitted = false",
            ).preparedStatementSetter { ps -> ps.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now())) }
            .rowMapper(notificationRowMapper())
            .build()

    @Bean
    fun notificationRowMapper(): RowMapper<PersonalNotificationDto> =
        RowMapper { rs: ResultSet, _: Int ->
            PersonalNotificationDto(
                id = rs.getLong("id"),
                sender = rs.getLong("sender"),
                title = rs.getString("title"),
                message = rs.getString("message"),
//                notificationTransferTime = rs.getTimestamp("notification_transfer_time").toLocalDateTime(),
                receiverEmail = rs.getString("email"),
            )
        }

    @Bean
    fun notificationProcessor(): ItemProcessor<PersonalNotificationDto, PersonalNotificationDto> =
        ItemProcessor {
            val future = kafkaTemplate.send("alarm", "reserved", it)

            try {
                future.join()
                logger.info { "Notification id : ${it.id} sent to: ${it.receiverEmail} with title: ${it.title} and message: ${it.message}" }
            } catch (e: CompletionException) {
                logger.error {
                    "Failed to send notification id : ${it.id} to: ${it.receiverEmail} with title: ${it.title} and message: ${it.message}"
                }
            }
            it
        }

    @Bean
    fun notificationWriter(): ItemWriter<PersonalNotificationDto> =
        ItemWriter { notifications ->
            val jdbcTemplate = NamedParameterJdbcTemplate(dataSource)
            val params =
                notifications.map { notification ->
                    MapSqlParameterSource().addValue("id", notification.id)
                }

            jdbcTemplate.batchUpdate("update notification set transmitted = true where id = :id", params.toTypedArray())
            notifications.forEach { notification ->
                logger.info {
                    "Notification sent to: ${notification.receiverEmail} with title: ${notification.title} and message: ${notification.message}"
                }
            }
        }
}