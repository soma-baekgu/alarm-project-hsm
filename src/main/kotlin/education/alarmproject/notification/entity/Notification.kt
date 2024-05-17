package education.alarmproject.notification.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val sender: String,
    val receiver: Long,
    val title: String,
    val message: String,
    val notificationTransferTime: LocalDateTime,
)
