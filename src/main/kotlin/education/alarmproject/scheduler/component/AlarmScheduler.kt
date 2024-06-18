// package education.alarmproject.scheduler.component
//
// import education.alarmproject.scheduler.config.AlarmJobConfig
// import org.springframework.batch.core.JobParametersBuilder
// import org.springframework.batch.core.JobParametersInvalidException
// import org.springframework.batch.core.launch.JobLauncher
// import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
// import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
// import org.springframework.batch.core.repository.JobRestartException
// import org.springframework.scheduling.annotation.Scheduled
// import org.springframework.stereotype.Component
//
// @Component
// class AlarmScheduler(
//    private val jobLauncher: JobLauncher,
//    private val alarmJobConfig: AlarmJobConfig,
// ) {
//    @Scheduled(cron = "0 0/1 * * * ?") // 1분마다 Job 실행
//    @Throws(
//        JobInstanceAlreadyCompleteException::class,
//        JobExecutionAlreadyRunningException::class,
//        JobParametersInvalidException::class,
//        JobRestartException::class,
//    )
//    fun runAlarmBatch() {
//        jobLauncher.run(
//            alarmJobConfig.job(),
//            JobParametersBuilder()
//                .toJobParameters(),
//        )
//    }
// }
