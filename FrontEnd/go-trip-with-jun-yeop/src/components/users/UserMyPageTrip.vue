<script setup>
import { onMounted, ref } from 'vue';
import { useMemberStore } from "@/stores/member";
import { getPlansById } from "@/api/attraction";
import { jwtDecode } from "jwt-decode";

import router from "@/router/index.js";

const userPlanList = ref([]);

let token = sessionStorage.getItem("accessToken");
let decodeToken = jwtDecode(token);

const getUserPlans = async () => {
  const res = await getPlansById(decodeToken.id);
  // console.log(res.data);
  res.data.forEach((onePlan) => {
    onePlan.planList.map((schedule) => ({
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
    userPlanList.value.push(onePlan);
  });
}

// const goSavedPlan = (id, title) => {
//   router.push({
//     name: "savedPlan",
//     state: {
//       userId: id,
//       planTitle: title,
//     }
//   })
// }

onMounted(async () => {
  await getUserPlans();
  // console.log(userPlanList.value);
});

</script>

<template>
<div class="MyPageWrapper">
  <div class="SavedPlanWrapper">

    <div
      class="SavedPlan"
      v-for="plan in userPlanList"
      :key="plan.id"
      :one-plan="plan"
    >
      <router-link :to="{
        name: 'savedPlan',
        params: { userId: plan.planUserId, planTitle: plan.planTitle } ,
        query: { userId: plan.planUserId, planTitle: plan.planTitle } ,
      }">
        <p>{{ plan.planTitle }}</p>
      </router-link>
    </div>

  </div>
</div>
</template>

<style scoped>
.MyPageWrapper{
  background-color: rgb(240, 240, 240);
  box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
  width: 160vh;
  height: 60vh;
  padding: 5vh;
  box-sizing: border-box;
  /* align-items: center; */
  text-align: center;
  margin-left: 5vh;
  border-radius: 2vh;
}

.SavedPlanWrapper{
  /* background-color: rgb(250, 250, 250); */
  padding: 0 5vh 0 0;
  height: 50vh;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  overflow-y: scroll;
  align-items: center;
}

.SavedPlan{
  background-color: rgb(201, 195, 186);
  box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
  width: 90%;
  height: 7vh;
  line-height: 7vh;
  padding: 0 3vh;
  box-sizing: border-box;
  font-size: 2.5vh;
  border-radius: 1vh;
  margin-bottom: 3vh;
  transition: 0.5s;
}

.SavedPlan:hover{
  background-color: rgb(184, 176, 166);
  width: 100%;
  height: 8vh;
  line-height: 8vh;
  transition: 0.5s;
}

.SavedPlan p{
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin: 0;
}


.SavedPlan a{
  color: rgb(250, 250, 250);
}

</style>