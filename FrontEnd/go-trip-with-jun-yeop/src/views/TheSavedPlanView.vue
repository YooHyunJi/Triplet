<script setup>
import { ref, onMounted, computed } from "vue";
import { VueDraggableNext } from "vue-draggable-next";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { jwtDecode } from "jwt-decode";
import { useRoute } from 'vue-router'


import VKakaoMap from "@/components/common/VKakaoMap.vue";
import AttractionSearch from "@/components/attraction/AttractionSearch.vue";
import PlanDay from "@/components/plan/PlanDay.vue";
import { savePlanToDB, getPlansFromDB } from "@/api/attraction";
import { useAttractionMenuStore } from "@/stores/attractionMenu";

const route = useRoute();

const memberStore = useMemberStore();
const attractionMenuStore = useAttractionMenuStore();

const { userInfo, myLikeAttrList } = storeToRefs(memberStore);
const { userGetLikeAttr } = memberStore;

const tripPeople = ref(1);
const likeList = ref([]);
const isLikeList = ref(false);
const dayList = ref([]);
const routeData = ref([]);
const findMarkers = ref([]);
const totalBudget = computed(() => {
  let totalWholeBudget = 0;
  dayList.value.forEach((day) => {
    day.attractionList.forEach((budget) => {
      totalWholeBudget += budget.attrBudget * 1;
    });
  });
  let totalPersonBudget = Math.floor(totalWholeBudget / tripPeople.value);
  totalPersonBudget = totalPersonBudget === Infinity ? 0 : totalPersonBudget;


  return [totalWholeBudget, totalPersonBudget];
});

const getPlan = async () => {
  const res = await getPlansFromDB(route.query.userId, route.query.planTitle);
  // console.log(res.data[0].planTitle);

  const InputPlanTitle = document.querySelector("#InputPlanTitle");
  InputPlanTitle.value = res.data[0].planTitle;

  tripPeople.value = res.data[0].planPeople;
  
  const parseData = res.data[0].planList.map((schedule) => ({
    attractionList: schedule.map((attraction) => ({
      attrId: attraction.attrId,
      attrImag: attraction.attrImag,
      attrName: attraction.attrName,
      attrAddr: attraction.attrAddr,
      attrLati: attraction.attrLati,
      attrLong: attraction.attrLong,
      attrType: attraction.attrType,
      attrBudget: attraction.attrBudget,
      attrMemo: attraction.attrMemo,
    })),
  }));
  dayList.value = parseData;
};

const handleUpdateSections = (sections) => {
  console.log("부모 컴포넌트에서 sections를 받음:", sections);
  routeData.value = sections;
};

// console.log(route.query.userId);
// console.log(route.query.planTitle);

onMounted(async () => {
  await userGetLikeAttr(route.query.userId);
  if (myLikeAttrList.value.length === 0) isLikeList.value = false;
  else isLikeList.value = true;
  likeList.value = myLikeAttrList.value;

  await getPlan();
  
});

</script>

<template>
  <div class="Wrapper">
    <div class="DivPlanWrapper">
      <div class="DivPlanWrapperHeader">
        <input
          type="text"
          id="InputPlanTitle"
          class="InputPlanTitle"
          disabled
        />
      </div>
      <div class="DivContent">
        <div class="DivLeft">
          <VKakaoMap class="DivMap" :routeData="routeData.value" />
        </div>
        <div class="DivRight">
          <div class="DivPlan">
            <div class="Title DivPlanTitle">Plan</div>
            <div class="DivPlanContent">
              <div class="DivPlanHeader">
                <div class="DivPlanPeople">
                  여행 인원은<input
                    type="number"
                    min="1"
                    v-model="tripPeople"
                    id="DivPlanPeople"
                    disabled
                  />명
                </div>
              </div>
              <div class="DivPlanBody">
                <PlanDay
                  v-for="(day, index) in dayList"
                  :key="day.dayCnt"
                  :one-day="day"
                  :index="index + 1"
                  :trip-people="tripPeople"
                  @remove-day="removeFromDay"
                  @remove-attraction="removeAttractionFromDay"
                  @updateSections="handleUpdateSections"
                />
              </div>
              <div class="DivPlanFooter">
                <div class="DivPlanTotalBudgetTotal">
                  총 예산
                  <p id="DayCount" class="DayCount">{{ totalBudget[0] }}</p>
                </div>
                <div class="DivPlanTotalBudgetPer">
                  1인당 예산
                  <p id="DayCount" class="DayCount">{{ totalBudget[1] }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.DivPlanWrapper {
  /* background-color: rgb(165, 13, 13); */
  background-color: rgb(240, 240, 240);
  box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
  box-sizing: border-box;
  border-radius: 5vh;
  width: 65%;
  height: 85vh;
  margin: 0 auto;
  padding: 5vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.DivPlanWrapperHeader {
  /* background-color: orange; */
  height: 7vh;
  display: flex;
  width: 80%;
  margin: 0 auto;
}

.InputPlanTitle {
  background-color: rgb(201, 195, 186);
  box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
  border: 0.4vh solid rgb(201, 195, 186);
  color: rgb(250, 250, 250);
  height: 7vh;
  flex: 1;
  margin: 0 auto 3vh auto;
  padding: 0 2vh;
  font-size: 2vh;
  transition: 0.4s;
  text-align: center;
  border-radius: 3.5vh;
}

.InputPlanTitle::placeholder {
  color: rgb(250, 250, 250);
}

.InputPlanTitle:focus {
  background-color: rgb(250, 250, 250);
  color: rgb(78, 74, 67);
  flex: 5;
  transition: 0.4s;
}

.InputPlanTitle:focus::placeholder {
  color: rgb(78, 74, 67);
}

.DivContent {
  /* background-color: green; */
  box-sizing: border-box;
  width: 100%;
  height: 65vh;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}

.DivLeft {
  /* background-color: pink; */
  flex: 1;
  display: flex;
  flex-direction: column;
}

.DivMap {
  height: 100%;
  border-radius: 5%;
  margin: 0 !important;
  box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
}

.DivRight {
  /* background-color: pink; */
  flex: 1;
  margin-left: 5vh;
}

.Title {
  border-radius: 5px 5px 0 0;
  box-sizing: border-box;
  text-align: center;
  font-size: 2vh;
  width: 100%;
  line-height: 5vh;
  box-shadow: 0px 5px 5px 0px rgb(230, 230, 230);
}

.DivPlan {
  background-color: rgb(250, 250, 250);
  box-shadow: 2px 5px 10px 2px rgb(199, 199, 199);
  flex: 1;
  border-radius: 1vh;
  height: 100%;
}

.DivPlanTitle {
  background: linear-gradient(rgb(122, 137, 145), rgb(115, 129, 136));
  color: rgb(240, 240, 240);
}

.DivPlanContent {
  /* background-color: gray; */
  padding: 1vh;
  height: 92%;
  display: flex;
  flex-wrap: wrap;
  box-sizing: border-box;
  flex-direction: column;
}

.DivPlanHeader {
  background-color: rgb(190, 203, 209);
  flex: 1;
  display: flex;
  align-content: center;
  justify-content: center;
  border-radius: 1vh;
}

.DivPlanPeople {
  margin: auto 0;
}

.DivPlanPeople input {
  font-family: "Cafe24Supermagic-Bold-v1.0";
  border: none;
  border-radius: 1vh;
  padding: 1vh;
  box-sizing: border-box;
  width: 5vh;
  text-align: center;
  margin: 0 1vh;
}

input::-webkit-inner-spin-button {
  appearance: none;
  -moz-appearance: none;
  -webkit-appearance: none;
}

.DivPlanPeople input:focus {
  border: none;
  outline: none;
}

.DivPlanPeople input:disabled {
  background: #F0F0F0;
}

.DivPlanBody {
  /* background-color: rgb(239, 126, 6); */
  flex: 8;
  overflow-y: scroll;
  margin: 1vh 0;
  /* padding: 0 1vh 0 0; */
}

.BtnAddPlan {
  background-color: rgb(193, 204, 207);
  width: 96%;
  border: none;
  height: 5vh;
  margin: 0;
  padding: 1vh;
  border-radius: 1vh;
  cursor: pointer;
}

.BtnAddPlan:hover {
  background-color: rgb(182, 191, 194);
  transition: 0.4s;
}

.DivPlanFooter {
  background-color: rgb(190, 203, 209);
  border-radius: 1vh;
  flex: 1;
  display: flex;
  text-align: center;
  align-content: center;
}

.DivPlanTotalBudgetTotal,
.DivPlanTotalBudgetPer {
  flex: 1;
  margin: auto 0;
}
</style>
