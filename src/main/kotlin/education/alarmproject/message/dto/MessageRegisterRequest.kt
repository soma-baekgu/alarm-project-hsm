package education.alarmproject.message.dto

data class MessageRegisterRequest(
    val sender: String,
    val receiver: String,
    val title: String,
    val message: String,
    val reservationMinute: Long,
)
