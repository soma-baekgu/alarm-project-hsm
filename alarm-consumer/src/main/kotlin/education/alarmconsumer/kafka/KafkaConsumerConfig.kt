package education.alarmconsumer.kafka

import education.alarmcore.dto.PersonalNotificationDto
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig {
    @Value("\${spring.kafka.bootstrap-server}")
    lateinit var bootstrapServer: String

    @Bean
    fun consumerConfig(): Map<String, Any> {
        val props: HashMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false

        return props
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, PersonalNotificationDto> {
        val deserializer = JsonDeserializer(PersonalNotificationDto::class.java)

        return DefaultKafkaConsumerFactory(
            consumerConfig(),
            StringDeserializer(),
            ErrorHandlingDeserializer(deserializer),
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<
        ConcurrentMessageListenerContainer<String, PersonalNotificationDto>,
    > {
        val factory: ConcurrentKafkaListenerContainerFactory<String, PersonalNotificationDto> =
            ConcurrentKafkaListenerContainerFactory()
        factory.consumerFactory = consumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE

        return factory
    }
}
