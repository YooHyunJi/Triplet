<script setup>
import { onMounted, ref } from 'vue';
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { jwtDecode } from "jwt-decode";
import { useAttractionInfoStore } from "@/stores/attractionInfo";

import LikedAttraction from "@/components/users/item/LikedAttraction.vue";

const attractionInfoStore = useAttractionInfoStore();
const { attrDelLikes } = attractionInfoStore;

const memberStore = useMemberStore();
const { userInfo, myLikeAttrList} = storeToRefs(memberStore);
const { userGetLikeAttr } = memberStore;

const likeAttrList = ref([])

const getLikeAttractions = async () => {
  await userGetLikeAttr(userInfo.value.id);
  likeAttrList.value = myLikeAttrList.value;
}

const cancelLike = async (thisAttrId) => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  const attrLikeinfo = {
    id: decodeToken.id,
    attrId: thisAttrId,
  };
  await attrDelLikes(attrLikeinfo);
  await getLikeAttractions();
}    

onMounted(async () => {
  // await userGetLikeAttr(userInfo.value.id);
  await getLikeAttractions();
})
</script>

<template>
<div class="MyPageWrapper">
  <div class="LikedAttractionWrapper">
    <LikedAttraction
      v-for="like in likeAttrList"
      :key="like.attrId"
      :oneAttr="like"
      @clickX="cancelLike"
    >

    </LikedAttraction>
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
  text-align: center;
  margin-left: 5vh;
  border-radius: 2vh;
}

.LikedAttractionWrapper{
  /* background-color: pink; */
  width: 100%;
  height: 50vh;
  box-sizing: border-box;
  display: flex;
  overflow-y: scroll;
  align-items: center;
  justify-content: space-evenly;
  flex-wrap: wrap;
}

</style>