package education.alarmcore.entity

import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class GroupNotificationEntity(
    sender: Long,
    title: String,
    message: String,
    notificationTransferTime: LocalDateTime,
    val receiver: Long,
) : NotificationEntity(
        sender = sender,
        title = title,
        message = message,
        notificationTransferTime = notificationTransferTime,
    )
