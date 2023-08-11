<script setup lang="ts">
import {ref} from "vue";

const aiPrompt = ref("")
const results = ref("No results yet")
const duration = ref("")
const doQuery = async () => {
  results.value = "Loading..."
  duration.value = ""
  try {
    const t1 = new Date()
    const result = await fetch(`${import.meta.env.VITE_API_URL}/prompt?q=${aiPrompt.value}`)
    const res = await result.json()
    results.value = res
    const t2 = new Date()
    duration.value = `${(t2.getTime() - t1.getTime())} ms`
  } catch (e) {
    console.log(e)
    results.value = "Oops!"
  }
}
</script>

<template>
  <div class="card">
    <h1>Request - Response</h1>
    <form @submit.prevent.stop="doQuery">
      <input type="text" v-model="aiPrompt"/>
      <button type="submit">Ask</button>
    </form>
    <p>{{ results }}</p>
    <p>{{ duration }}</p>
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
