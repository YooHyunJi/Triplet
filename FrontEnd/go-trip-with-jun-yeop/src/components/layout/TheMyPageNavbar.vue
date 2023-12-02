<script setup>
import TheDraggableView from "@/views/TheDraggableView.vue";
</script>

<template>
  <nav>
    <ul>
      <li><router-link :to="{name: 'user-modify'}">회원 정보 수정</router-link></li>
      <li><router-link :to="{name: 'user-mytrip'}">나의 여행 계획</router-link></li>
      <li><router-link :to="{name: 'user-mytripattr'}">저장된 여행지</router-link></li>
      <li><router-link :to="{name: 'user-myboard'}">저장된 게시글</router-link></li>
    </ul>
  </nav>
</template>

<style scoped>
ul{
  background-color: rgb(175, 184, 188);
  height: 45vh;
  width: 30vh;
  padding: 4.5vh 3vh;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  text-align: center;
  border-radius: 2vh;
	box-shadow: 2px 5px 10px 2px rgb(220, 220, 220);
  margin: 0;
}

li a{
  /* background-color: pink; */
  color: rgb(240, 240, 240);
  line-height: 9vh;
  font-size: 2.2vh;
}

li a:hover{
  color: rgb(78, 78, 78);
  transition: 0.4s;
}
</style>
