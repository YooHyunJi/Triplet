<script setup>
import { ref, watch, onMounted } from "vue";
import { VueDraggableNext } from "vue-draggable-next";
import { useAttractionMenuStore } from "@/stores/attractionMenu";
import { useRoute } from "vue-router";

const attractionMenuStore = useAttractionMenuStore();

const props = defineProps({
	oneAttraction: Object,
  index: Number,
  key: String,
});

const emit = defineEmits([
  'removeAttraction',
]);

const route = useRoute();

const clickX = (index, attrId) => {
  emit('removeAttraction', index, attrId);
}

const isReadonly = (obj) => {
  if (obj.constructor === Object
    && Object.keys(obj).length === 0) {
    return false;
  }
  
  return true;
}

// console.log(props.oneAttraction);
let image;
attractionMenuStore.contentTypeList.forEach((a) => {
  if(props.oneAttraction.attrType*1 === a.value*1){
    image = a.image;
    return;
  }
});

onMounted(() => {
  // console.log(route);
  // console.log(route.params);
  if (isReadonly(route.params)) {
    const btns = document.querySelectorAll(".BtnAttractionRemove");
    btns.forEach((b) => { b.style.display = "none"; });
    const budget = document.querySelectorAll(".budget");
    budget.forEach((b) => { b.disabled = true; });
    const memo = document.querySelectorAll(".memo");
    memo.forEach((m) => {m.disabled = true; });
    
    // btn.style.display = "none";
  }
});
</script>

<template>
	<div class="DivPlanAttraction" @click="attractionMenuStore.viewAttraction(props.oneAttraction)">
		<div class="DivPlanAttractionHeader">
			<div :id="props.key" class="DivAttractionImg" v-html="image"></div>
			<div class="DivAttractionInfo">
				<div class="DivAttractionTitle"><p>{{ oneAttraction.attrName }}</p></div>
				<div class="DivAttractionAddress"><p>{{ oneAttraction.attrAddr }}</p></div>
			</div>
			<button type="button" class="BtnAttractionRemove" @click="clickX(index, oneAttraction.attrId)">X</button>
		</div>
		<div class="DivPlanAttractionBody">
			<div class="DivAttractionBudget">
        <input
          type="number"
          min="0"
          v-model="oneAttraction.attrBudget"
          placeholder="예상 예산을 입력해주세요!"
          class="budget"
        >
      </div>
			<div class="DivAttractionMemo">
        <textarea
          v-model="oneAttraction.attrMemo"
          placeholder="자유롭게 메모를 작성해보세요!"
          class="memo"
          wrap=on
        >
        </textarea>
      </div>
		</div>
	</div>
</template>

<style>
.DivPlanAttractionHeader{
  height: 7vh;
  width: 100%;
  display: flex;
  align-items: center;
}

.DivAttractionImg{
  background-color: rgb(236, 236, 236);
  border-radius: 50%;
  width: 7vh;
  height: 7vh;
  line-height: 7vh;
  text-align: center;
  font-size: 4vh;
  box-sizing: border-box;
  margin-right: 1vh;
}

.DivAttractionInfo{
  /* background-color: pink; */
  padding: 0.3vh 0;
  box-sizing: border-box;
  width: 70%;
  
  display: flex;
  flex-direction: column;
}

.DivAttractionInfo p{
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin: 0;
}

.DivAttractionInfo .DivAttractionTitle{
  /* background-color: red; */
  max-height: 4vh;
  font-size: 1.7vh;
  overflow: hidden;
}

.DivAttractionInfo .DivAttractionAddress{
  /* background-color: green; */
  max-height: 2vh;
  font-size: 1.5vh;
  overflow: hidden;
}

.BtnAttractionRemove{
  margin-bottom: auto;
  border: 0;
  padding: 0;
  cursor: pointer;
  color: rgb(233, 233, 233);
  background: none;
  font-weight: bold;
  margin-left: auto;
}

.DivPlanAttractionBody{
  display: flex;
  flex-direction: column;
}

.DivPlanAttractionBody input, textarea{
  font-family: 'Pretendard-Regular';
  border: none;
  border-radius: 1vh;
  padding: 1vh;
  box-sizing: border-box;
  width: 100%;
}

.DivPlanAttractionBody input, textarea:focus{
  border: none;
  outline: none;
}

.DivPlanAttractionBody input, textarea:disabled {
    background: #F0F0F0;
}

.DivAttractionBudget{
  display: flex;
  justify-content: space-between;
  margin: 1vh 0;
}

.DivAttractionMemo{
  height: 10vh;
}

.DivAttractionMemo textarea{
  height: 100%;
  resize: none;
}
</style>
