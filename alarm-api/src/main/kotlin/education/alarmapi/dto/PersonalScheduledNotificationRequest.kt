package education.alarmapi.dto

class PersonalScheduledNotificationRequest(
    val sender: Long,
    val receiver: Long,
    val title: String,
    val message: String,
    val reservationMinute: Long,
)
