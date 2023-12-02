<script setup>
import { ref, onMounted, computed } from "vue";
import router from "@/router";
import { VueDraggableNext } from "vue-draggable-next";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { jwtDecode } from "jwt-decode";
import { useAttractionMenuStore } from "@/stores/attractionMenu";

import VKakaoMap from "@/components/common/VKakaoMap.vue";
import AttractionSearch from "@/components/attraction/AttractionSearch.vue";
import PlanDay from "@/components/plan/PlanDay.vue";
import { savePlanToDB, getPlansFromDB } from "@/api/attraction";

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
      // console.log(budget);
      // console.log(budget.hasOwnProperty("attrBudget"));
      if (budget.hasOwnProperty("attrBudget")) totalWholeBudget += budget.attrBudget * 1;
    });
  });
  let totalPersonBudget = Math.floor(totalWholeBudget / tripPeople.value);
  totalPersonBudget = totalPersonBudget === Infinity ? 0 : totalPersonBudget;

  return [totalWholeBudget, totalPersonBudget];
});

const addDay = () => {
  dayList.value.push({
    attractionList: ref([]),
  });
}
const removeFromDay = (dayCnt) => {
  dayList.value.forEach((day, i) => {
    if (i + 1 === dayCnt) {
      dayList.value.splice(i, 1);
    }
  });
};

const removeAttractionFromDay = (dayCnt, attrId) => {
  dayList.value.forEach((day, i) => {
    if(i+1 === dayCnt){
      day.attractionList.forEach((attr, j) => {
        if (attr.attrId === attrId) {
          day.attractionList.splice(j, 1);
        }
      });
    }
  });
};

const savePlan = async () => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);

  const title = document.querySelector("#InputPlanTitle").value;
  if (!title || title.replace(/^\s+|\s+$/g, "") == "") {
    alert("여행 제목을 입력해주세요!");
    return;
  }

  const people = document.querySelector("#DivPlanPeople").value*1;

  const arr = [];
  dayList.value.forEach((day) => {
    arr.push(day.attractionList);
  });

  const planInfo = {
    planUserId: decodeToken.id,
    planTitle: title,
    planPeople: people,
    planList: arr,
  };
  await savePlanToDB(planInfo);
  alert("여행 계획이 정상적으로 저장되었습니다!");
  router.push({ path: "/mytrip", });
};

const likeAttrList = ref([]);
onMounted(async () => {
  await userGetLikeAttr(userInfo.value.id);
  if (myLikeAttrList.value.length === 0) isLikeList.value = false;
  else isLikeList.value = true;

  likeList.value = myLikeAttrList.value;
});
const handleUpdateSections = (sections) => {
  // sections를 이용한 필요한 작업 수행
  // findMarkers.value = coordinates;
  routeData.value = sections;
  // 여기서 필요한 로직을 수행하세요.
};
</script>

<template>
  <div class="Wrapper">
    <div class="DivPlanWrapper">
      <div class="DivPlanWrapperHeader">
        <div class="DivPlanHeaderLeft"></div>
        <input
          type="text"
          id="InputPlanTitle"
          class="InputPlanTitle"
          placeholder="여행 계획의 이름을 입력해주세요!"
        />
        <div class="DivPlanHeaderRight">
          <button type="button" @click="savePlan">저장</button>
        </div>
      </div>

      <div class="DivContent">
        <div class="DivLeft">
          <VKakaoMap class="DivMap" :routeData="routeData.value" />
        </div>

        <div class="AttractionList">
          <AttractionSearch />
          <div class="DivLiked">
            <div class="Title DivLikedTitle">Liked Places</div>
            <div v-show="isLikeList" class="DivLikeResult DivLikeResultYes">
              <VueDraggableNext
                class="DragLike"
                :list="likeList"
                :group="{ name: 'attractions', pull: 'clone', put: false }"
              >
              <div
                v-for="like in likeList"
                :key="like.attrId"
                class="DivLikeList"
                @click="attractionMenuStore.viewAttraction(like)"
              >
                  {{ like.attrName }}
                </div>
              </VueDraggableNext>
            </div>
            <div v-show="!isLikeList" class="DivLikeResult DivLikeResultNo">
              검색 결과가 없습니다
            </div>
          </div>
        </div>

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

              <button type="button" class="BtnAddPlan" @click="addDay">
                +
              </button>
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
</template>

<style scoped>
.DivPlanWrapper {
  /* background-color: rgb(165, 13, 13); */
  background-color: rgb(240, 240, 240);
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
  border-radius: 2vh;
  width: 95%;
  height: 87vh;
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
  /* margin-bottom: 3vh; */
}

.DivPlanHeaderLeft {
  /* background-color: red; */
  flex: 1;
}

.InputPlanTitle {
  background-color: rgb(201, 195, 186);
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
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
  outline: none;
  color: rgb(78, 74, 67);
  flex: 5;
  transition: 0.4s;
}

.InputPlanTitle:focus::placeholder {
  color: rgb(78, 74, 67);
}

.DivPlanHeaderRight {
  text-align: right;
  flex: 1;
}

.DivPlanHeaderRight button{
  background-color: rgba(194, 48, 48, 0.8) !important; 
}

.DivPlanHeaderRight button {
  width: 10vh;
  height: 7vh;
  border-radius: 2vh;
  background-color: rgba(122, 133, 139, 0.5);
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
  color: rgb(240, 240, 240);
}

.DivPlanHeaderRight button:hover {
  background-color: rgba(122, 133, 139, 0.7);
  transition: 0.4s;
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
  flex: 1.3;
  display: flex;
  flex-direction: column;
}

.DivMap {
  height: 100%;
  border-radius: 2vh;
  margin: 0 !important;
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
}

.AttractionList {
  flex: 1;
  margin: 0 2vh;
  height: 65vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  /* overflow: hi; */
}

.DivSearch {
  background-color: rgb(243, 243, 243);
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
  padding: 1vh;
  box-sizing: border-box;
  border-radius: 1vh;
  width: 100%;
  height: 38vh;
  margin: 0 0 2vh 0;
}

.Title {
  border-radius: 5px 5px 0 0;
  box-sizing: border-box;
  text-align: center;
  font-size: 2vh;
  width: 100%;
  line-height: 5vh;
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
}

.DivLikedTitle {
  background: linear-gradient(rgb(122, 137, 145), rgb(115, 129, 136));
  color: rgb(240, 240, 240);
}

.DivLiked {
  height: 25vh;
}

.DivLikeResult {
  background-color: rgb(250, 250, 250);
  padding: 1vh;
  box-sizing: border-box;
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
  flex: 1;
  border-radius: 0 0 1vh 1vh;
}

.DragLike {
  /* height: 100%; */

  height: 18vh;
  margin: 0 auto;
  overflow-y: scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 1vh;
  box-sizing: border-box;
}

.DivLikeList {
  background-color: rgb(230, 230, 230);
  /* height: 5vh; */
  width: 100%;
  padding: 1.5vh;
  box-sizing: border-box;
  border-radius: 1vh;
  cursor: pointer;
  margin-bottom: 1vh;
}

.DivPlan {
  background-color: rgb(250, 250, 250);
  box-shadow: 0.5vh 0.7vh 1vh 0.5vh rgba(0, 0, 0, 0.1);
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
  font-family: 'Pretendard-Regular';
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
