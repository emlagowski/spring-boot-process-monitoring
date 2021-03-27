package io.github.emlagowski.springdatabasemetrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.binder.db.DatabaseTableMetrics
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class DatabaseMetrics(final val meterRegistry: MeterRegistry, final val dataSource: DataSource) {

    init {
        val processStateAllTag = Tag.of("processState", "all")
        val processStateRegisteredTag = Tag.of("processState", State.REGISTERED.name)
        val processStateInProgressTag = Tag.of("processState", State.IN_PROGRESS.name)
        val processStateAutoTag = Tag.of("processState", State.AUTO.name)
        val processStateManualTag = Tag.of("processState", State.MANUAL.name)
        val processStateClosedTag = Tag.of("processState", State.CLOSED.name)
        val tableName = "process"
        val dataSourceName = "default"
        DatabaseTableMetrics.monitor(meterRegistry, dataSource, dataSourceName, tableName, listOf(processStateAllTag))
        DatabaseTableMetrics(dataSource, "SELECT COUNT(1) FROM $tableName where state = '${State.REGISTERED.name}'", dataSourceName, tableName, listOf(processStateRegisteredTag)).bindTo(meterRegistry)
        DatabaseTableMetrics(dataSource, "SELECT COUNT(1) FROM $tableName where state = '${State.IN_PROGRESS.name}'", dataSourceName, tableName, listOf(processStateInProgressTag)).bindTo(meterRegistry)
        DatabaseTableMetrics(dataSource, "SELECT COUNT(1) FROM $tableName where state = '${State.AUTO.name}'", dataSourceName, tableName, listOf(processStateAutoTag)).bindTo(meterRegistry)
        DatabaseTableMetrics(dataSource, "SELECT COUNT(1) FROM $tableName where state = '${State.MANUAL.name}'", dataSourceName, tableName, listOf(processStateManualTag)).bindTo(meterRegistry)
        DatabaseTableMetrics(dataSource, "SELECT COUNT(1) FROM $tableName where state = '${State.CLOSED.name}'", dataSourceName, tableName, listOf(processStateClosedTag)).bindTo(meterRegistry)
    }
}