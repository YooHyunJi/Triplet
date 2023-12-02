<script setup>
import { onMounted, ref } from 'vue';
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

import BoardListItem from "../boards/item/BoardListItem.vue";

const memberStore = useMemberStore();
const { userInfo, myLikeBoardList} = storeToRefs(memberStore);
const { userGetLikeBoard } = memberStore;


const likeBoardList = ref([])

onMounted(async () => {
  await userGetLikeBoard(userInfo.value.id);
  likeBoardList.value = myLikeBoardList.value;
})
</script>

<template>
<div class="MyPageWrapper">
  <div class="LikedArticleWrapper">
    <div
      class="LikedArticle"
      v-for="like in likeBoardList"
      :key="like.boardNo"
      :article="like">
      <router-link :to="{ name: 'article-view', params: { articleno: like.boardNo } }">
        <p>{{ like.subject }}</p>
      </router-link>
    </div>
    <!-- <div
      class="LikedArticle"
      v-for="like in likeBoardList"
      :key="like.boardNo"
      :article="like">
      <router-link :to="{ name: 'article-view', params: { articleno: like.boardNo } }">
        <p>{{ like.subject }}</p>
      </router-link>
    </div>
    <div
      class="LikedArticle"
      v-for="like in likeBoardList"
      :key="like.boardNo"
      :article="like">
      <router-link :to="{ name: 'article-view', params: { articleno: like.boardNo } }">
        <p>{{ like.subject }}</p>
      </router-link>
    </div> -->
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

.LikedArticleWrapper{
  /* background-color: rgb(250, 250, 250); */
  padding: 0 5vh 0 0;
  height: 50vh;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  overflow-y: scroll;
  align-items: center;
}

.LikedArticle{
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

.LikedArticle:hover{
  background-color: rgb(184, 176, 166);
  width: 100%;
  height: 8vh;
  line-height: 8vh;
  transition: 0.5s;
}

.LikedArticle p{
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin: 0;
}


.LikedArticle a{
  color: rgb(250, 250, 250);
}

</style>