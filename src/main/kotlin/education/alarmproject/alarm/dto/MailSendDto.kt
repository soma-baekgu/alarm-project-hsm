package education.alarmproject.alarm.dto

data class MailSendDto(
    val from: String,
    val to: String,
    val title: String,
    val message: String,
)
