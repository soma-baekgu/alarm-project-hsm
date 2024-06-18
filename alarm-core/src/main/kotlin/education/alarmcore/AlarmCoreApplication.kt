package education.alarmcore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AlarmCoreApplication

fun main(args: Array<String>) {
    runApplication<AlarmCoreApplication>(*args)
}
