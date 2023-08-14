package com.example.aidemo

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.service.OpenAiService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.LocalDateTime
import java.util.UUID
import kotlin.concurrent.thread

@RestController
@CrossOrigin("*")
@RequestMapping("stream-prompt")
class AiStreamPromptController(
    private val aiService: OpenAiService,
    @Value("\${sse.timeout.seconds}") private val timeout: Long,
    @Value("\${sse.reconnect.seconds}") private val reconnect: Long
) {

    private val logger by lazy { LoggerFactory.getLogger(AiStreamPromptController::class.java) }

    @GetMapping
    fun getStreamPrompt(q: String): SseEmitter {
        val emitter = SseEmitter(timeout * 1000)
        thread {
            val completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(listOf(ChatMessage("user", q)))
                .stream(true)
                .build()
            aiService
                .streamChatCompletion(completionRequest)
                .doOnError {
                    logger.warn("{}", it)
                }
                .blockingForEach { chunk ->
                    emitter.send(
                        SseEmitter.event()
                            .id("${chunk.id}-${LocalDateTime.now()}")
                            .name("chunk")
                            .data(chunk.choices.map { completion -> completion.message })
                            .reconnectTime(reconnect * 1000)
                    )
                }
            emitter.send(
                SseEmitter
                    .event()
                    .id("${UUID.randomUUID()}")
                    .name("done")
                    .data("true")
            )
            emitter.complete()
        }
        return emitter
    }
}
