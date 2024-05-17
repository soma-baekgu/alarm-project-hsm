package education.alarmproject.user.controller

import education.alarmproject.user.dto.UserRegisterRequest
import education.alarmproject.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Controller
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/users")
    fun registerUser(
        @RequestBody request: UserRegisterRequest,
    ): ResponseEntity<Unit> {
        val location =
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userService.registerUser(request))
                .toUri()

        return ResponseEntity.created(location).build()
    }
}
