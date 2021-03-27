package io.github.emlagowski.springdatabasemetrics

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/process")
class ProcessController(val processRepository: ProcessRepository) {
    @PostMapping
    fun get(@RequestBody processRequest: ProcessRequest): ProcessCreationResponse {
        val request = Process(name = processRequest.name, state = processRequest.state)
        val savedProcess = processRepository.save(request)
        return ProcessCreationResponse(savedProcess.id)
    }
}

data class ProcessRequest(val name: String, val state: State)

data class ProcessCreationResponse(val id: Int)