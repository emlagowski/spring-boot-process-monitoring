package io.github.emlagowski.springdatabasemetrics

import io.micrometer.core.annotation.Counted
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import javax.transaction.Transactional

@Configuration
@EnableScheduling
class ProcessScheduler(private final val processService: ProcessService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Counted
    @Scheduled(fixedRateString = "\${process.initEveryMillis}")
    @Transactional
    fun initializeProcess() {
        logger.info("Running scheduled initializeProcess")
        processService.initializeProcess()
    }

    @Counted
    @Transactional
    @Scheduled(fixedRateString = "\${process.moveEveryMillis}")
    fun moveFirstProcessFurther() {
        logger.info("Running scheduled moveFirstProcessFurther")
        processService.moveFirstProcessFurther()
    }
}