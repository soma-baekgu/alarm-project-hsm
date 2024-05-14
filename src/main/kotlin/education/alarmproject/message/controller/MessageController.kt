package education.alarmproject.message.controller

import education.alarmproject.message.dto.MessageRegisterRequest
import education.alarmproject.message.service.MessageService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(
    private val messageService: MessageService,
) {
    @PostMapping
    fun sendMail(
        @RequestBody request: MessageRegisterRequest,
    ) {
        messageService.registerMessage(request)
    }
}
