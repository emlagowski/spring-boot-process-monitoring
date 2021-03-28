package io.github.emlagowski.springdatabasemetrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.binder.db.DatabaseTableMetrics
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class ProcessDatabaseMetrics(private val meterRegistry: MeterRegistry, private val dataSource: DataSource) {

    private final val tableName: String = "process"
    private final val dataSourceName: String = "default"
    private final val tagName: String = "processState"

    init {
        monitorAll()
        monitor(State.REGISTERED)
        monitor(State.IN_PROGRESS)
        monitor(State.AUTO)
        monitor(State.MANUAL)
        monitor(State.CLOSED)
    }

    private fun monitorAll() {
        DatabaseTableMetrics.monitor(
            meterRegistry,
            dataSource,
            dataSourceName,
            tableName,
            listOf(Tag.of(tagName, "all"))
        )
    }

    private fun monitor(state: State) {
        DatabaseTableMetrics(
            dataSource,
            "SELECT COUNT(1) FROM $tableName where state = '${state.name}'",
            dataSourceName,
            tableName,
            listOf(Tag.of(tagName, state.name))
        ).bindTo(meterRegistry)
    }
}