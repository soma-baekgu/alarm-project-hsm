package education.alarmproject.user.service

import education.alarmproject.user.dto.UserRegisterRequest
import education.alarmproject.user.entity.User
import education.alarmproject.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun registerUser(request: UserRegisterRequest): Long {
        return userRepository.save(
            User(
                email = request.email,
            ),
        ).id
    }

    fun findByIdIn(ids: List<Long>): List<User> {
        return userRepository.findByIdIn(ids = ids)
    }
}
