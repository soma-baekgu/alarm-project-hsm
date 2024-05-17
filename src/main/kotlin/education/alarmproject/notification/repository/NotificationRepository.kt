package education.alarmproject.notification.repository

import education.alarmproject.notification.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findMessagesByNotificationTransferTime(currentHourAndMinute: LocalDateTime): List<Notification>
}
