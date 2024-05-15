package education.alarmproject.rabbitmq.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.rabbitmq")
data class RabbitMqProperties(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
)
