package education.alarmcore.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class PersonalNotificationDto
    @JsonCreator
    constructor(
        @JsonProperty("id") val id: Long,
        @JsonProperty("sender") val sender: Long,
        @JsonProperty("title") val title: String,
        @JsonProperty("message") val message: String,
        @JsonProperty("receiverEmail") val receiverEmail: String,
    )
