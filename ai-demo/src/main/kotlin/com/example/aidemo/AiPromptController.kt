package com.example.aidemo

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.service.OpenAiService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin("*")
@RequestMapping("prompt")
class AiPromptController(private val aiService: OpenAiService) {

    @GetMapping
    fun getPrompt(q: String): List<String> {
        val completionRequest = ChatCompletionRequest.builder()
            .model("gpt-3.5-turbo")
            .messages(listOf(ChatMessage("user", q)))
            .build()
        return aiService.createChatCompletion(completionRequest)
            .choices.map { it.message.content }
    }
}
