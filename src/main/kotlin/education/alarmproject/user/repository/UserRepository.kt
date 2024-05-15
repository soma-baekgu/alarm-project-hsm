package education.alarmproject.user.repository

import education.alarmproject.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
