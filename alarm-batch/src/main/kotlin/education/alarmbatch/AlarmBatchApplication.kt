package education.alarmbatch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["education"])
class AlarmBatchApplication

fun main(args: Array<String>) {
    runApplication<AlarmBatchApplication>(*args)
}
