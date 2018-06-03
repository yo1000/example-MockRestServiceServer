package com.yo1000.example.mockrestserviceserver

import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.WebApplicationContext

/**
 *
 * @author yo1000
 */
@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class MockRestServiceServerTest {
    @Autowired
    lateinit var context: WebApplicationContext
    @Autowired
    lateinit var restTemplate: RestTemplate

    @Test
    fun test() {
        val mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build()

        MockRestServiceServer.bindTo(restTemplate).build().let {
            it.expect(MockRestRequestMatchers.requestTo(
                    "http://localhost:8081/server/a"
            )).andExpect(
                    MockRestRequestMatchers.method(HttpMethod.GET)
            ).andRespond(MockRestResponseCreators.withSuccess("""
                {
                  "example": "A"
                }
                """.trimIndent(), MediaType.APPLICATION_JSON_UTF8
            ))

            it.expect(MockRestRequestMatchers.requestTo(
                    "http://localhost:8081/server/b"
            )).andExpect(
                    MockRestRequestMatchers.method(HttpMethod.GET)
            ).andRespond(MockRestResponseCreators.withSuccess("""
                {
                  "example": "B"
                }
                """.trimIndent(), MediaType.APPLICATION_JSON_UTF8
            ))
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/client"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize<Int>(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].example").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].example").value("B"))
    }
}