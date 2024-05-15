package education.alarmproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@ConfigurationPropertiesScan
@EnableScheduling
@SpringBootApplication
class AlarmProjectApplication

fun main(args: Array<String>) {
    runApplication<AlarmProjectApplication>(*args)
}
