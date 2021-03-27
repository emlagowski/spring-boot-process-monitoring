package io.github.emlagowski.springdatabasemetrics

import org.springframework.data.repository.CrudRepository

interface ProcessRepository: CrudRepository<Request, Int> {
}