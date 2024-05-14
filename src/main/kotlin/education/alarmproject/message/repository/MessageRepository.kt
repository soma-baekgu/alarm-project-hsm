package education.alarmproject.message.repository

import education.alarmproject.message.entity.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MessageRepository : JpaRepository<Message, Long> {
    fun findMessagesByAlarmTransferTime(currentHourAndMinute: LocalDateTime): List<Message>
}
