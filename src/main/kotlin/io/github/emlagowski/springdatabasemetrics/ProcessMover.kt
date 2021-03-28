package io.github.emlagowski.springdatabasemetrics

import io.micrometer.core.annotation.Counted
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import javax.transaction.Transactional

@Configuration
@EnableScheduling
class ProcessMover(final val processRepository: ProcessRepository, final val processMoveCounter: ProcessMoveCounter) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Counted()
    @Scheduled(fixedRateString = "\${process.initEveryMillis}")
    @Transactional
    fun init() {
        val process = Process(name = "", state = State.REGISTERED)
        val savedProcess = processRepository.save(process)
        processMoveCounter.increase(null, State.REGISTERED)
        logger.info("Initialized process with id=${savedProcess.id}")
    }

    @Transactional
    @Scheduled(fixedRateString = "\${process.moveEveryMillis}")
    fun move() {
        processRepository.findFirstByStateNot(State.CLOSED)?.let {
            val from = it.state
            val to = it.moveToRandomNextStep()
            processMoveCounter.increase(from, to)
            logger.info("Moved to state $it")
        }
    }
}