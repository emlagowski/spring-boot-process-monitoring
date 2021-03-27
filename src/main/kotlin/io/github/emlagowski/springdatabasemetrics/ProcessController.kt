package io.github.emlagowski.springdatabasemetrics

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/process")
class ProcessController(val processRepository: ProcessRepository) {
    @PostMapping
    fun get(@RequestBody processRequest: ProcessRequest): ProcessCreationResponse {
        val request = Request(name = processRequest.name, type = processRequest.type)
        val savedProcess = processRepository.save(request)
        return ProcessCreationResponse(savedProcess.id)
    }
}

data class ProcessRequest(val name: String, val type: Type)

data class ProcessCreationResponse(val id: Int)