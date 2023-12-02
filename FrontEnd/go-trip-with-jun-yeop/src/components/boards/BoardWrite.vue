<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="BoardWrapper">
    <div class="BoardTitle"><h1>게시글 작성</h1></div>
    <div><BoardFormItem type="regist" /></div>
  </div>
</template>

<style scoped>
.BoardWrapper{
  text-align: center;
}
</style>
