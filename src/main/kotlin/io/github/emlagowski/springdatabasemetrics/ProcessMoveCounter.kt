package io.github.emlagowski.springdatabasemetrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.springframework.stereotype.Component

@Component
class ProcessMoveCounter(final val meterRegistry: MeterRegistry) {

    private final val metricName: String = "process_movement"

    private final val registered: String = "REGISTERED"
    private final val registeredToInProgress: String = "REGISTERED->IN_PROGRESS"
    private final val inProgressToAuto: String = "IN_PROGRESS->AUTO"
    private final val inProgressToManual: String = "IN_PROGRESS->MANUAL"
    private final val autoToClosed: String = "AUTO->CLOSED"
    private final val manualToClosed: String = "MANUAL->CLOSED"

    private var registeredCounter: Counter = meterRegistry.counter(metricName, listOf(Tag.of("type", registered)))
    private var registeredToInProgressCounter: Counter = meterRegistry.counter(metricName, listOf(Tag.of("type", registeredToInProgress)))
    private var inProgressToAutoCounter: Counter = meterRegistry.counter(metricName, listOf(Tag.of("type", inProgressToAuto)))
    private var inProgressToManualCounter: Counter = meterRegistry.counter(metricName, listOf(Tag.of("type", inProgressToManual)))
    private var autoToClosedCounter: Counter = meterRegistry.counter(metricName, listOf(Tag.of("type", autoToClosed)))
    private var manualToClosedCounter: Counter = meterRegistry.counter(metricName, listOf(Tag.of("type", manualToClosed)))

    fun increase(from: State?, to: State) {
        when {
            to == State.REGISTERED -> registeredCounter.increment()
            to == State.IN_PROGRESS -> registeredToInProgressCounter.increment()
            to == State.AUTO -> inProgressToAutoCounter.increment()
            to == State.MANUAL -> inProgressToManualCounter.increment()
            from == State.AUTO && to == State.CLOSED -> autoToClosedCounter.increment()
            from == State.MANUAL && to == State.CLOSED -> manualToClosedCounter.increment()
        }
    }

}