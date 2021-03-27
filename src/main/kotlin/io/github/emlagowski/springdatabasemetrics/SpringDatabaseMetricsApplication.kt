package io.github.emlagowski.springdatabasemetrics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDatabaseMetricsApplication

fun main(args: Array<String>) {
	runApplication<SpringDatabaseMetricsApplication>(*args)
}
