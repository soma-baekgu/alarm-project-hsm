package education.alarmapi.dto

class PersonalImmediateNotificationRequest(
    val sender: Long,
    val receiver: Long,
    val title: String,
    val message: String,
)
