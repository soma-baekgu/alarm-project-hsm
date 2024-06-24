package education.alarmcore.repository

import education.alarmcore.entity.PersonalNotificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface PersonalNotificationRepository : JpaRepository<PersonalNotificationEntity, Long> {
    fun findAllByNotificationTransferTimeAfterAndTransmittedFalse(currentTime: LocalDateTime): List<PersonalNotificationEntity>
}
