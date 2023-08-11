<template>
  <div class="left-right">
    <single-request class="item"></single-request>
    <stream-result class="item"></stream-result>
  </div>
</template>

<script setup lang="ts">
import SingleRequest from "./components/single-request.vue";
import StreamResult from "./components/stream-result.vue";
</script>

<style scoped>
  .left-right {
    display: flex;
  }
  .item {
    margin: 2em;
    max-width: 30vw;
  }

</style>
