package education.alarmproject.scheduler.config

import education.alarmproject.message.service.MessageService
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class AlarmJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val messageService: MessageService,
    private val mailSender: MailSender,
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
        return StepBuilder("step", jobRepository)
            .tasklet(tasklet(), transactionManager)
            .allowStartIfComplete(true) // 이 설정을 통해 Step이 항상 재시작될 수 있도록 함
            .build()
    }

    fun tasklet(): Tasklet {
        return Tasklet { _, _ ->
            messageService.findMessagesToTransfer().forEach {
                mailSender.send(
                    SimpleMailMessage().apply {
                        from = it.recipeint
                        setTo(it.recipeint)
                        subject = it.title
                        text = it.message
                    },
                )
            }
            null
        }
    }
}
