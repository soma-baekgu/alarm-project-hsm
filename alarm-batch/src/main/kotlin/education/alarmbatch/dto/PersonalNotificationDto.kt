package education.alarmbatch.dto

data class PersonalNotificationDto(
    val id: Long,
    val sender: Long,
    val title: String,
    val message: String,
    val receiverEmail: String,
)
