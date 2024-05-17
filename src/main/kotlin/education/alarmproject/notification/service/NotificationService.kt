package education.alarmproject.notification.service

import education.alarmproject.notification.dto.NotificationRegisterRequest
import education.alarmproject.notification.entity.Notification
import education.alarmproject.notification.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class NotificationService(
    private val notificationRepository: NotificationRepository,
) {
    @Transactional
    fun registerNotification(request: NotificationRegisterRequest) {
        notificationRepository.save(
            Notification(
                sender = request.sender,
                receiver = request.receiver,
                title = request.title,
                message = request.message,
                notificationTransferTime =
                    LocalDateTime.now()
                        .plusMinutes(request.reservationMinute)
                        .withSecond(0)
                        .withNano(0),
            ),
        )
    }

    fun findNotificationToTransfer(): List<Notification> {
        val currentHourAndMinute = LocalDateTime.now().withSecond(0).withNano(0)
        return notificationRepository.findMessagesByNotificationTransferTime(
            currentHourAndMinute = currentHourAndMinute,
        )
    }
}
