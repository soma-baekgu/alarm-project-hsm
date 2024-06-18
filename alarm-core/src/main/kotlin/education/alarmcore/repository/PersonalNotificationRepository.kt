package education.alarmcore.repository

import education.alarmcore.entity.PersonalNotification
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface PersonalNotificationRepository : JpaRepository<PersonalNotification, Long> {
    fun findAllByNotificationTransferTimeAfterAndTransmittedFalse(currentTime: LocalDateTime): List<PersonalNotification>
}
