package io.github.emlagowski.springdatabasemetrics

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/process")
class ProcessController(val processService: ProcessService) {
    @PostMapping
    fun get(): ProcessCreationResponse {
        val initializedProcess = processService.initializeProcess()
        return initializedProcess.toResponse()
    }
}

private fun Process.toResponse(): ProcessCreationResponse {
    return ProcessCreationResponse(this.id)
}

data class ProcessCreationResponse(val id: Int)