package education.alarmproject.message.service

import education.alarmproject.message.dto.MessageRegisterRequest
import education.alarmproject.message.entity.Message
import education.alarmproject.message.repository.MessageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class MessageService(
    private val messageRepository: MessageRepository,
) {
    @Transactional
    fun registerMessage(request: MessageRegisterRequest) {
        messageRepository.save(
            Message(
                sender = request.sender,
                receiver = request.receiver,
                title = request.title,
                message = request.message,
                alarmTransferTime =
                    LocalDateTime.now()
                        .plusMinutes(request.reservationMinute)
                        .withSecond(0)
                        .withNano(0),
            ),
        )
    }

    fun findMessagesToTransfer(): List<Message> {
        val currentHourAndMinute = LocalDateTime.now().withSecond(0).withNano(0)
        return messageRepository.findMessagesByAlarmTransferTime(
            currentHourAndMinute = currentHourAndMinute,
        )
    }
}
