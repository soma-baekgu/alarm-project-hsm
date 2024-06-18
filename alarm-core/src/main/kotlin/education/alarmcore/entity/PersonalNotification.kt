package education.alarmcore.entity

import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class PersonalNotification(
    sender: Long,
    title: String,
    message: String,
    notificationTransferTime: LocalDateTime,
    val receiver: Long,
) : Notification(
        sender = sender,
        title = title,
        message = message,
        notificationTransferTime = notificationTransferTime,
    )
