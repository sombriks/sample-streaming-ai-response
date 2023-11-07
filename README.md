# [sample-streaming-ai-response](https://github.com/sombriks/sample-streaming-ai-response)

investigating proper openai chat streaming responses over Server-Sent Events

## requirements

- java 17
- node 18

## how to run this sample

run backend:

```bash
cd ai-demo
./gradlew build bootRun
```

run frontend:

```bash
cd ui-web
npm install
npm run dev
```

## leads

- https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#event_stream_format
- https://github.com/TheoKanning/openai-java/blob/main/example/src/main/java/example/OpenAiApiExample.java
- https://dzone.com/articles/server-sent-events-using-spring
- https://start.spring.io/
- https://vitejs.dev/guide/
- https://openai.com/blog/new-models-and-developer-products-announced-at-devday

## noteworthy

- lots of wrap/unwrap properties on both sides, `EventSource` and
  `aiService.streamChatCompletion`.
- stream indeed has better output time, although it takes longer to finish
- a good react wrapper for event source is recommended
- chat model `gpt-4-1106-preview` is consistently faster than `gpt-3.5-turbo`
