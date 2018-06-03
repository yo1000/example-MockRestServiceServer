package com.yo1000.example.mockrestserviceserver

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class MockRestServiceServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(MockRestServiceServerApplication::class.java, *args)
}

@Configuration
class ExampleConfiguration {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    fun restTemplate(): RestTemplate = RestTemplate()
}

@RestController
@RequestMapping("/client")
class ExampleClientController(
        val restTemplate: RestTemplate
) {
    @GetMapping
    fun get(): Any {
        val mapA = restTemplate.getForObject("http://127.0.0.1:8081/server/a", Map::class.java)
        val mapB = restTemplate.getForObject("http://127.0.0.1:8081/server/b", Map::class.java)

        return listOf(mapA, mapB)
    }
}
