<script setup lang="ts">
import {ref} from "vue";
import {nanoid} from "nanoid";

type Result = {
  id: string;
  data: any;
  delta: string;
}

type EventData = {
  content: string
}

const aiPrompt = ref("")
const results = ref<Result[]>([])

const doQuery = async () => {
  results.value = []
  const evtSource = new EventSource(`${import.meta.env.VITE_API_URL}/stream-prompt?q=${aiPrompt.value}`);
  const d1 = new Date()
  evtSource.addEventListener("chunk", event => {
    console.log(event)
    const data = JSON.parse(event.data).map((c: EventData) => c.content).join("")
    const result = {id: nanoid(), data, delta: ` ${new Date().getTime() - d1.getTime()} ms`}
    results.value = [...results.value, result]
  })
  // backend should inform that the prompt is over
  evtSource.addEventListener("done", () => evtSource.close())
  // setTimeout(evtSource.close, 30000) // TODO
}

</script>

<template>
  <div class="card">
    <h1>Request - SSE</h1>
    <form @submit.prevent.stop="doQuery">
      <input type="text" v-model="aiPrompt"/>
      <button type="submit">Ask</button>
    </form>
    <p>
      <span v-for="result in results" :key="result.id">{{ result.data }}</span>
    </p>
    <p>
      <span v-for="result in results" :key="result.id">{{ result.delta }}</span>
    </p>
  </div>
</template>

<style scoped>
form {
  display: flex;
}

input {
  flex-grow: 1;
}
</style>
