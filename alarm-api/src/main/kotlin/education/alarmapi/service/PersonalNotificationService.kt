package education.alarmapi.service

import education.alarmapi.dto.PersonalImmediateNotificationRequest
import education.alarmapi.dto.PersonalScheduledNotificationRequest
import education.alarmcore.dto.PersonalNotificationDto
import education.alarmcore.entity.PersonalNotificationEntity
import education.alarmcore.repository.PersonalNotificationRepository
import education.alarmcore.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.concurrent.CompletionException

private val logger = KotlinLogging.logger {}

@Service
class PersonalNotificationService(
    private val notificationRepository: PersonalNotificationRepository,
    private val kafkaTemplate: KafkaTemplate<String, PersonalNotificationDto>,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun registerNotification(request: PersonalScheduledNotificationRequest) {
        notificationRepository.save(
            PersonalNotificationEntity(
                sender = request.sender,
                title = request.title,
                message = request.message,
                notificationTransferTime =
                    LocalDateTime
                        .now()
                        .plusMinutes(request.reservationMinute)
                        .withSecond(0)
                        .withNano(0),
                receiver = request.receiver,
            ),
        )
    }

    fun sendNotification(request: PersonalImmediateNotificationRequest) {
        val userEntity =
            userRepository.findByIdOrNull(request.receiver) ?: run {
                logger.error { "User not found : ${request.receiver}}" }
                throw EntityNotFoundException("User not found : ${request.receiver}")
            }

        val it =
            PersonalNotificationDto(
                id = 0,
                sender = request.sender,
                receiverEmail = userEntity.email,
                title = request.title,
                message = request.message,
            )
        val future = kafkaTemplate.send("alarm", "immediate", it)

        try {
            future.join()
            logger.info { "Notification id : ${it.id} sent to: ${it.receiverEmail} with title: ${it.title} and message: ${it.message}" }
        } catch (e: CompletionException) {
            logger.error {
                "Failed to send notification id : ${it.id} to: ${it.receiverEmail} with title: ${it.title} and message: ${it.message}, error: ${e.message}"
            }
            throw e
        }
    }
}
