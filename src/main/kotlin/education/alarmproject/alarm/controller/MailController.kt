package education.alarmproject.alarm.controller

import education.alarmproject.alarm.dto.MailSendDto
import education.alarmproject.alarm.service.MailService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MailController(
    private val mailService: MailService,
) {
    @PostMapping
    fun sendMail(
        @RequestBody mailRegisterDto: MailSendDto,
    ) {
        mailService.mailSend(mailRegisterDto)
    }
}
