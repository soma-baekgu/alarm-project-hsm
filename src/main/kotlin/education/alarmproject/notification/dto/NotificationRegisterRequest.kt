package education.alarmproject.notification.dto

data class NotificationRegisterRequest(
    val sender: String,
    val receiver: Long,
    val title: String,
    val message: String,
    val reservationMinute: Long,
)
