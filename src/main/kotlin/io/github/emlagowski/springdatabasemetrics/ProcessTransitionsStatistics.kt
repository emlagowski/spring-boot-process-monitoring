package io.github.emlagowski.springdatabasemetrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.springframework.stereotype.Component

@Component
class ProcessTransitionsStatistics(private final val meterRegistry: MeterRegistry) {

    private final val metricName: String = "process_transitions"
    private final val stageTag: String = "stage"

    fun newProcessInitialized() {
        meterRegistry
            .counter(metricName, listOf(Tag.of(stageTag, State.REGISTERED.name)))
            .increment()
    }

    fun newProcessTransition(from: State, to: State) {
        meterRegistry
            .counter(metricName, listOf(Tag.of(stageTag, "${from.name}->${to.name}")))
            .increment()

    }

}