package education.alarmconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["education"])
class AlarmConsumerApplication

fun main(args: Array<String>) {
    runApplication<AlarmConsumerApplication>(*args)
}
