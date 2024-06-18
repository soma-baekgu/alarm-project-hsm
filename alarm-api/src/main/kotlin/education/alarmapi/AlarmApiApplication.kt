package education.alarmapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["education"])
class AlarmApiApplication

fun main(args: Array<String>) {
    runApplication<AlarmApiApplication>(*args)
}
