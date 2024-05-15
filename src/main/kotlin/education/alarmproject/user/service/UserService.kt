package education.alarmproject.user.service

import education.alarmproject.user.dto.UserRegisterRequest
import education.alarmproject.user.entity.User
import education.alarmproject.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun registerUser(userRegisterRequest: UserRegisterRequest): Long {
        return userRepository.save(
            User(
                email = userRegisterRequest.email,
            ),
        ).id
    }
}
