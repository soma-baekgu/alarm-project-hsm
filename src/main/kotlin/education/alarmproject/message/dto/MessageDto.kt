package education.alarmproject.message.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class MessageDto
    @JsonCreator
    constructor(
        @JsonProperty("sender") val sender: String,
        @JsonProperty("receiver") val receiver: String,
        @JsonProperty("title") val title: String,
        @JsonProperty("message") val message: String,
    )
