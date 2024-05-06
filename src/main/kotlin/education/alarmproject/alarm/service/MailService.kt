package education.alarmproject.alarm.service

import education.alarmproject.alarm.dto.MailSendDto
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service

@Service
class MailService(
    private val mailSender: MailSender,
) {
    fun mailSend(mailSendDto: MailSendDto) {
        mailSender.send(
            SimpleMailMessage().apply {
                from = mailSendDto.from
                setTo(mailSendDto.to)
                subject = mailSendDto.title
                text = mailSendDto.message
            },
        )
    }
}
