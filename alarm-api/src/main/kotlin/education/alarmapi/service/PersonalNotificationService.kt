package education.alarmapi.service

import education.alarmapi.dto.PersonalScheduledNotificationRequest
import education.alarmcore.entity.PersonalNotification
import education.alarmcore.repository.PersonalNotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PersonalNotificationService(
    private val notificationRepository: PersonalNotificationRepository,
) {
    @Transactional
    fun registerNotification(request: PersonalScheduledNotificationRequest) {
        notificationRepository.save(
            PersonalNotification(
                sender = request.sender,
                title = request.title,
                message = request.message,
                notificationTransferTime =
                    LocalDateTime.now()
                        .plusMinutes(request.reservationMinute)
                        .withSecond(0)
                        .withNano(0),
                receiver = request.receiver,
            ),
        )
    }
}
