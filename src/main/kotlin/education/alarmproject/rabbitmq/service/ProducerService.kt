// package education.alarmproject.rabbitmq.service
//
// import com.fasterxml.jackson.core.JsonProcessingException
// import com.fasterxml.jackson.databind.ObjectMapper
// import education.alarmproject.notification.dto.NotificationDto
// import org.springframework.amqp.rabbit.core.RabbitTemplate
// import org.springframework.stereotype.Service
//
// @Service
// class ProducerService(
//    private val rabbitTemplate: RabbitTemplate,
// ) {
//    fun sendMessage(notificationDto: NotificationDto) {
//        try {
//            // 객체를 JSON으로 변환
//            val objectMapper = ObjectMapper()
//            val objectToJSON = objectMapper.writeValueAsString(notificationDto)
//            rabbitTemplate.convertAndSend("sample.exchange", "sample.key", objectToJSON)
//        } catch (jpe: JsonProcessingException) {
//            println(jpe.message)
//            println("파싱 오류 발생")
//        }
//    }
// }
