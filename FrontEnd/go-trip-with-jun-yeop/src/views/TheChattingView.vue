<script setup>
import chat from "@/components/chat/Chatting.vue";
import { useRoute } from 'vue-router'
import { ref } from "vue";
const route = useRoute();
const roomName = ref("");
roomName.value = route.query.name;
</script>

<template>
  <div>
    <chat :roomName="roomName"/>
    <router-view></router-view>
  </div>
</template>

<style></style>
