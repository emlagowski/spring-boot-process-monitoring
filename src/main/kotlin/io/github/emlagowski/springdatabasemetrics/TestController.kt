package io.github.emlagowski.springdatabasemetrics

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping
    fun get(): String {
        return "elo"
    }
}