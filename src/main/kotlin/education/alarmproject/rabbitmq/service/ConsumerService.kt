// package education.alarmproject.rabbitmq.service
//
// import com.fasterxml.jackson.databind.ObjectMapper
// import education.alarmproject.notification.dto.NotificationDto
// import org.springframework.amqp.rabbit.annotation.RabbitListener
// import org.springframework.mail.MailSender
// import org.springframework.mail.SimpleMailMessage
// import org.springframework.stereotype.Service
// import java.awt.SystemColor.text
//
// @Service
// class ConsumerService(
//    private val mailSender: MailSender,
// ) {
//    @RabbitListener(queues = ["sample.queue"])
//    fun receiveMessage(it: String) {
//        val objectMapper = ObjectMapper()
//        val message = objectMapper.readValue(it, NotificationDto::class.java)
//        mailSender.send(
//            SimpleMailMessage().apply {
//                from = message.sender
//                setTo(message.receiver)
//                subject = message.title
//                text = message.message
//            },
//        )
//    }
// //}
