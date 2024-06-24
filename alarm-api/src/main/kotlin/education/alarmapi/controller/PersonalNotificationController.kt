package education.alarmapi.controller

import education.alarmapi.dto.PersonalImmediateNotificationRequest
import education.alarmapi.dto.PersonalScheduledNotificationRequest
import education.alarmapi.service.PersonalNotificationService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RequestMapping("/api/personal-notifications")
@RestController
class PersonalNotificationController(
    val personalNotificationService: PersonalNotificationService,
) {
    @PostMapping("/scheduled")
    fun createScheduledPersonalNotification(
        @RequestBody request: PersonalScheduledNotificationRequest,
    ) {
        logger.info {
            "Creating scheduled personal notification: " +
                "sender : ${request.sender} receiver : ${request.receiver}"
        }

        personalNotificationService.registerNotification(request)
    }

    @PostMapping("/immediate")
    fun createImmediatePersonalNotification(
        @RequestBody request: PersonalImmediateNotificationRequest,
    ) {
        logger.info {
            "Creating immediate personal notification: " +
                "sender : ${request.sender} receiver : ${request.receiver}"
        }

        personalNotificationService.sendNotification(request)
    }
}
