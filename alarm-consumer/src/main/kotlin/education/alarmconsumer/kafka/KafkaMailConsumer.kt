package education.alarmconsumer.kafka

import education.alarmcore.dto.PersonalNotificationDto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

private val logger = KotlinLogging.logger {}

@Component
class KafkaMailConsumer(
    private val mailSender: MailSender,
) {
    @KafkaListener(groupId = "group001", topics = ["alarm"], concurrency = "3")
    fun mailConsumer(
        record: ConsumerRecord<String, PersonalNotificationDto>,
        ack: Acknowledgment,
        consumer: Consumer<String, PersonalNotificationDto>,
    ) {
        logger.info { "key=${record.key()}, id=${record.value().id}" }

        CompletableFuture.runAsync {
            try {
                mailSender.send(
                    SimpleMailMessage().apply {
                        from = record.value().sender.toString()
                        setTo(record.value().receiverEmail)
                        subject = record.value().title
                        text = record.value().message
                    },
                )
                ack.acknowledge()
            } catch (e: Exception) {
                logger.error { "error=${e.message}" }
            } finally {
                consumer.commitSync()
            }
        }
    }
}
