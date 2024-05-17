package education.alarmproject.notification.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class NotificationDto
    @JsonCreator
    constructor(
        @JsonProperty("sender") val sender: String,
        @JsonProperty("receiver") val receiver: String,
        @JsonProperty("title") val title: String,
        @JsonProperty("message") val message: String,
    )
