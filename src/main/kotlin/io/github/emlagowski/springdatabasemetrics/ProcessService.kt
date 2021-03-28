package io.github.emlagowski.springdatabasemetrics

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ProcessService(
    val processRepository: ProcessRepository,
    val processTransitionsStatistics: ProcessTransitionsStatistics
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun initializeProcess(): Process {
        val newProcess = Process.create()
        processTransitionsStatistics.newProcessInitialized()
        logger.info("New process initialized with id=${newProcess.id} and state=${newProcess.state}")
        return processRepository.save(newProcess)
    }

    fun moveFirstProcessFurther() {
        processRepository.findFirstByStateNot(State.CLOSED)
            ?.let {
                val oldState = it.state
                val newState = it.moveToRandomNextStep()
                logger.info("Process with id=${it.id} transitioned from oldState=${oldState} to newState=${newState}")
                Pair(oldState, newState)
            }?.let { (from, to) ->
                processTransitionsStatistics.newProcessTransition(from = from, to = to)
            }
    }
}