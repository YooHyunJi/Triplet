<script setup>
import router from "@/router";

const props = defineProps({
  loc: Object,
})

const moveToChatting = () => {
  router.push({
    name: 'chatting', query:{name : props.loc.name }
  });
}
</script>

<template>
<div className="CardWrapper" @click="moveToChatting">
  <div class="CardPin">
    <div class="CardPinBottom"></div>
    <div class="CardPinTop"></div>
  </div>
  <div class="CardImg">
    <img :src="props.loc.img" alt="" />
  </div>
  <div class="CardDesc">
    <!-- <router-link :to="{ name: 'chatting', query:{name : props.loc.name }}" >{{ props.loc.name }}</router-link> -->
    <p class="CardDescTop">{{ props.loc.name }}</p>
    <p class="CardDescBottom">{{ props.loc.desc }}</p>
  </div>
</div>
</template>

<style>
.CardWrapper{
  background-color: rgb(250, 250, 250);
  width: 28vh;
  height: 42vh;
  border-radius: 2vh;
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
  cursor: pointer;
  display: flex;
  padding: 2vh;
  box-sizing: border-box;
  flex-direction: column;
  align-items: center;
  margin: 7vh 6vh;
  transition: 0.5s;
}

.CardWrapper:hover{
  /* box-shadow: 0.5vh 0.5vh 1vh 0.5vh rgb(240, 240, 240); */
  transform: scale(1.02);
  transition: 0.5s;
}

.CardPin{
    /* background-color: gray; */
    width: 6vh;
    height: 6vh;
    margin: -5.5vh 0 0 0;
    top: 2vh;
    right: 2vh;
    position: relative;
}

.CardPin div{
  background-color: rgb(207, 53, 53);
  /* background-color: rgb(245, 172, 0); */
}

.CardPin .CardPinBottom{
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.2);
  width: 6vh;
  height: 6vh;
  border-radius: 50%;
}

.CardPin .CardPinTop{
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
  width: 5vh;
  height: 5vh;
  border-radius: 50%;
  z-index: 50;
  margin: -7vh 0 0 -1vh;
  /* position: fixed; */
}

.CardImg{
/* background-color: orange; */
width: 100%;
height: 33vh;
overflow: hidden;
}

.CardImg img{
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 0.5vh;
}

.CardDesc{
  /* background-color: green; */
  padding: 1vh;
  box-sizing: border-box;
  font-size: 3vh;
  width: 100%;
  height: 8vh;
  text-align: left;
  display: flex;
  flex-direction: column;
}

.CardDesc p{
  margin: 0;
}

.CardDesc .CardDescTop{
  /* background-color: rgb(78, 109, 78); */
  font-size: 2.7vh;
  line-height: 4vh;
}

.CardDesc .CardDescBottom {
  /* background-color: rgb(75, 239, 75); */
  font-size: 1.7vh;
  margin: 0;
}
</style>
