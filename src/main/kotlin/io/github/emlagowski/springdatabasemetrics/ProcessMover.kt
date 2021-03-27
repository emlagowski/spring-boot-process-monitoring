package io.github.emlagowski.springdatabasemetrics

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import javax.transaction.Transactional

@Configuration
@EnableScheduling
class ProcessMover(final val processRepository: ProcessRepository) {

    @Scheduled(fixedRateString = "\${process.initEveryMillis}")
    @Transactional
    fun init() {
        val process = Process(name = "", state = State.REGISTERED)
        val savedProcess = processRepository.save(process)
        println("Initialized process with id=${savedProcess.id}")
    }

    @Transactional
    @Scheduled(fixedRateString = "\${process.initEveryMillis}")
    fun move() {
        processRepository.findFirstByStateNot(State.CLOSED)?.moveToRandomNextStep().let { println("Moved to state $it") }
    }

}