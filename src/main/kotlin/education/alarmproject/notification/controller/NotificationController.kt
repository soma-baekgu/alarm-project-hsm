package education.alarmproject.notification.controller

import education.alarmproject.notification.dto.NotificationRegisterRequest
import education.alarmproject.notification.service.NotificationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationController(
    private val notificationService: NotificationService,
) {
    @PostMapping("/notifications")
    fun sendMail(
        @RequestBody request: NotificationRegisterRequest,
    ) {
        notificationService.registerNotification(request)
    }
}
