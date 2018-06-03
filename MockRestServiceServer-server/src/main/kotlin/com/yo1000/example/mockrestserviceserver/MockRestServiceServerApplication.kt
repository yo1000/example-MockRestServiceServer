package com.yo1000.example.mockrestserviceserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class MockRestServiceServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(MockRestServiceServerApplication::class.java, *args)
}

@RestController
@RequestMapping("/server")
class ExampleServerController {
    @GetMapping("/a")
    fun getA(): Any {
        return mapOf<String, String>(
                "example" to "A"
        )
    }

    @GetMapping("/b")
    fun getB(): Any {
        return mapOf<String, String>(
                "example" to "B"
        )
    }
}