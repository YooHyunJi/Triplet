<script setup>

import { onMounted, ref } from 'vue';
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";


const props = defineProps({
  oneAttr: Object,
});

// const emits = defineEmits([
//   "clickBtn"
// ]);

// const clickBtn = () => {

// }

const imgSrc =
  props.oneAttr.attrImag ?
    props.oneAttr.attrImag :
    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAA1BMVEXNzs4XqC2+AAAASElEQVR4nO3BgQAAAADDoPlTX+AIVQEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwDcaiAAFXD1ujAAAAAElFTkSuQmCC";

</script>

<template>
<div class="OneAttrWrapper">
  <div class="CloseBtn"><button type="button" @click="$emit('clickX', props.oneAttr.attrId)">X</button></div>
  <div class="AttrImg">
    <img :src="imgSrc" />
  </div>
  <div class="AttrDesc">
    <div class="AttrName">{{ props.oneAttr.attrName }}</div>
    <!-- {{ props.oneAttr.attrId }}<br/> -->
    <div class="AttrAddr">{{ props.oneAttr.attrAddr }}</div>
    <!-- {{ props.oneAttr.attrLati }} , {{ props.oneAttr.attrLong }}<br/> -->
    <!-- <div class="AttrType">{{ props.oneAttr.attrType }}</div> -->
  </div>
</div>
  
</template>

<style scoped>

.OneAttrWrapper{
  background-color: rgb(250, 250, 250);
  box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
  width: 30vh;
  height: 33vh;
  padding: 0 3vh 3vh 3vh;
  box-sizing: border-box;
  text-align: center;
  border-radius: 2vh;
  margin: 3vh 1.5vh;
}

.OneAttrWrapper:hover{
  transform: scale(1.03);
  transition: 0.5s;
}

.CloseBtn{
  /* background-color: green; */
  height: 3vh;
  text-align: right;
  margin: 1vh -2vh 0.5vh 0;
}

.CloseBtn button{
  background-color: rgba(122, 39, 39, 0) !important;
  color: rgb(150, 150, 150) !important;
  /* box-shadow: none !important; */
  width: 3vh;
  height: 3vh;
}

.AttrImg{
  /* background-color: red; */
  width: 24vh;
  height: 16vh;
  box-shadow: 2vh 2vh 10vh 2vh rgb(230, 230, 230);
}

.AttrImg img{
  width: 24vh;
  height: 16vh;
  object-fit: cover;
}

.AttrDesc{
  /* background-color: orange; */
  display: flex;
  flex-direction: column;
  height: 9.5vh;
}

.AttrName{
  /* background-color: skyblue; */
  line-height: 2.5vh;
  margin: 1.5vh 0 1vh 0;
}

.AttrAddr{
  /* background-color: green; */
  line-height: 2vh;
  font-size: 1.5vh;
}
</style>