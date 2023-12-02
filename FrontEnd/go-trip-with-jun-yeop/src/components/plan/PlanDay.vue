<script setup>
import { ref, watch, onMounted, computed } from "vue";
import { VueDraggableNext } from "vue-draggable-next";

import PlanAttraction from "./PlanAttraction.vue";
import { findMultiRoute } from "@/api/attraction";
import { storeToRefs } from "pinia";
import { useAttractionInfoStore } from "@/stores/attractionInfo";
const pathStore = useAttractionInfoStore();
const { pathSection, pathMarkers } = storeToRefs(pathStore);

const props = defineProps({
	oneDay: Object,
  index: Number,
  tripPeople: Number,
});

const emit = defineEmits([
  'removeDay',
  'removeAttraction'
]);

const dayBudget = computed(() => {
  let dayWholeBudget = 0;
  props.oneDay.attractionList.forEach((budget) => {
    // console.log(budget);
    // console.log(budget.hasOwnProperty("attrBudget"));
    if (budget.hasOwnProperty("attrBudget")) dayWholeBudget += budget.attrBudget * 1;
    // console.log(dayWholeBudget);
  });
  let dayPersonBudget = Math.floor(dayWholeBudget / props.tripPeople);
  dayPersonBudget = dayPersonBudget === Infinity ? 0 : dayPersonBudget;

  return [dayWholeBudget, dayPersonBudget];
});

const clickX = (index) => {
  emit('removeDay', index);
}

const removeFromList = (index, attrId) => {
  emit('removeAttraction', index, attrId);
}

const findPath = () => {
  if (props.oneDay.attractionList.length < 2) {
    // 여행지가 두 개 미만일 경우 처리하지 않음
    console.log("여행지가 두 개 이상이어야 경로 찾지 !!! 아오 진짜");
  }
  else {
    // 여행지가 두 개 이상을 경우 경로 찾기
    const coordinates = []
    props.oneDay.attractionList.forEach((attraction, index) => { 
      coordinates.push({ lat: attraction.attrLong, lon: attraction.attrLati})
    })
    pathMarkers.value = coordinates;
      findMultiRoute(
        coordinates,
        (response) => {
          // map에 오버레이가 떠 있는 상태면 제거
          const showedOverlay = document.querySelector(".wrap");
          if(showedOverlay) showedOverlay.remove();
          
          const sections = [];
          response.data.routes[0].sections.forEach((section) => {
            sections.push(section)
          })
          pathSection.value = sections;
        },
        (error) => {
          // 실패 시 동작
          console.error("실패:", error);
        }
      );

  }
}

</script>

<template>
<div class="DivPlanDay">
	<div class="DivPlanDayHeader">
    <div class="DivPlanDayHeaderLeft">
      <div class="DivPlanDateCount">
        DAY 
        <p id="DayCount" class="DayCount">{{ index }}</p>
      </div>
      <button type="button" class="BtnFindPath" @click="findPath">경로 계산</button>
    </div>
    <button type="button" class="BtnCloseDay" @click="clickX(index)">X</button>
	</div>

	<VueDraggableNext
		class="DivPlanAttractionList DragPlan"
		:list="oneDay.attractionList"
		group="attractions"
		item-key="name"
	>
    <PlanAttraction
      v-for="attraction in oneDay.attractionList"
      :key="attraction.attrId"
      :one-attraction="attraction"
      :index="index"
      @remove-attraction="removeFromList"
    />
	</VueDraggableNext>

	<div class="DivPlanDayBudget">
		<div class="DivPlanDayBudgetTotal">
			총 예산<p id="DayCount" class="DayCount">{{ dayBudget[0] }}</p>
		</div>
		<div class="DivPlanDayBudgetPer">
			1인당 예산<p id="DayCount" class="DayCount">{{ dayBudget[1] }}</p>
		</div>
	</div>
</div>
</template>

<style>
.DivPlanAttractionList{
  /* background-color: gray; */
  margin: 1vh 0;
  /* padding: 1vh; */
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.DivPlanAttraction{
  background-color: rgb(178, 190, 196);
  border-radius: 1vh;
  display: flex;
  flex-direction: column;
  padding: 1vh;
  margin-bottom: 1vh;
}

.DivPlanDay{
  background-color: rgba(190, 203, 209, 0.4);
  margin: 0 1vh 3vh 0;
  padding: 1vh;
  border-radius: 1vh;
}

.DivPlanDayHeader{
  /* background-color: orange; */
  height: 3vh;
  display: flex;
  justify-content: space-between;
}

.DivPlanDayHeaderLeft{
  /* background-color: red; */
  display: flex;
  justify-content: space-between;
}

.DivPlanDateCount{
  background-color: rgba(194, 48, 48, 0.6);
  box-shadow: 2px 5px 10px 2px rgb(225, 225, 225);
  color: rgb(240, 240, 240);
  padding: 0 1vh;
  
  width: auto;
  line-height: 3vh;
  display: flex;
  justify-content: space-evenly;
  border-radius: 1vh;
  margin-right: 0.7vh;
}

.DayCount{
  margin: 0;
}

.BtnFindPath{
  background-color: rgba(177, 174, 162, 0.8);
  box-shadow: 2px 5px 10px 2px rgb(225, 225, 225);
  color: rgb(250, 250, 250);
  border-radius: 1vh;
  font-size: 1.5vh;
}

.BtnCloseDay{
  border: 0;
  padding: 0;
  cursor: pointer;
  color: rgb(177, 174, 162);
  background: none;
  font-weight: bold;
  padding: 0 1vh;
}

.DivPlanDayBudget{
  background-color: rgba(190, 203, 209, 0.8);
  flex: 1;
  display: flex;
  text-align: center;
  padding: 1vh 0;
  align-content: center;
  border-radius: 1vh;
}

.DivPlanDayBudgetTotal, .DivPlanDayBudgetPer{
  flex: 1;
  margin: auto 0;
  font-size: 1.7vh;
}
</style>
