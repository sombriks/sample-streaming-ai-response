package com.example.aidemo

import com.theokanning.openai.service.OpenAiService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class OpenAiConfiguration {

    @Value("\${openai.token}")
    private lateinit var token: String
    @Value("\${openai.timeout.seconds}")
    private var timeout: Long = 60

    @Bean("aiService")
    fun getOpenAiService() = OpenAiService(token, Duration.ofSeconds(timeout))
}
