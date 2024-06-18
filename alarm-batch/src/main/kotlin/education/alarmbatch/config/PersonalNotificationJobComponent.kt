package education.alarmbatch.config

import education.alarmbatch.entity.UserRepository
import education.alarmcore.entity.PersonalNotification
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime

@Configuration
class PersonalNotificationJobComponent(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val userRepository: UserRepository,
) {
    @Bean
    fun job(): Job {
        return JobBuilder("job", jobRepository)
            .incrementer(RunIdIncrementer()) // RunIdIncrementer를 추가하여 매번 다른 JobParameters를 사용
            .start(step())
            .build()
    }

    @Bean
    fun step(): Step {
        return StepBuilder("personalNotificationStep2", jobRepository)
            .chunk<PersonalNotification, PersonalNotificationLog>(1000, transactionManager)
            .reader(notificationReader())
            .processor(notificationProcessor())
            .writer(notificationWriter())
            .build()
    }

    @Bean
    fun notificationReader(): JpaCursorItemReader<PersonalNotification> {
        return JpaCursorItemReaderBuilder<PersonalNotification>()
            .name("personalNotificationReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString(
                "SELECT pn FROM PersonalNotification pn " +
                    "WHERE pn.notificationTransferTime <= :currentTime AND pn.transmitted = false",
            )
            .parameterValues(mapOf("currentTime" to LocalDateTime.now()))
            .build()
    }

    @Bean
    fun notificationProcessor(): ItemProcessor<PersonalNotification, PersonalNotificationLog> {
        return ItemProcessor { notification ->
            println("hello")
            val user = userRepository.findById(notification.receiver).orElse(null)
            if (user != null) {
                PersonalNotificationLog(
                    sender = notification.sender,
                    title = notification.title,
                    message = notification.message,
                    notificationTransferTime = notification.notificationTransferTime,
                    receiverEmail = user.email,
                )
            } else {
                null
            }
        }
    }

    @Bean
    fun notificationWriter(): ItemWriter<PersonalNotificationLog> {
        return ItemWriter { notifications ->
            notifications.forEach { notification ->
                // Log the notification details
                println(
                    "Notification sent to: ${notification.receiverEmail} with title: ${notification.title} and message: ${notification.message}",
                )
            }
        }
    }
}

data class PersonalNotificationLog(
    val sender: Long,
    val title: String,
    val message: String,
    val notificationTransferTime: LocalDateTime,
    val receiverEmail: String,
)
