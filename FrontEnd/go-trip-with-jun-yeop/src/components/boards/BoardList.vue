<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle, searchArticle, listArticlePg } from "@/api/board.js";
import { useMemberStore } from "@/stores/member";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/boards/item/BoardListItem.vue";
import VPageNavigation from "@/components/common/VPageNavigation.vue";

const router = useRouter();

const selectOption = ref([
  { text: "▼ 검색 조건", value: "" },
  { text: "글 번호", value: "1" },
  { text: "글 제목", value: "2" },
  { text: "닉네임", value: "3" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const param1 = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  content: "",
  condition: "",
});


onMounted(() => {
  getArticleListPg();
});

const changeKey = (val) => {
  param1.value.condition = val;
};


const getArticleListPg = async () => {
  listArticlePg(
    param1.value,
    ({ data }) => {
      articles.value = data.list;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
};
const getSearchList = () => {
  param1.value.pgno = 1;
  getArticleListPg();
}
const onPageChange =  (val) => {
  currentPage.value = val;
  param1.value.pgno = val;
  getArticleListPg();
};

const moveWrite = () => {
  router.push({ name: "article-write" });
};
</script>

<template>
<div class="BoardWrapper">
  <div class="BoardTitle"><h1>여행 게시판</h1></div>
  <div class="BoardContent">
    <div class="BoardContentHeader">
      <div class="BoardWrite"><button type="button" @click="moveWrite">글쓰기</button></div>
      <div class="BoardSearch">
        <VSelect :selectOption="selectOption" @onKeySelect="changeKey" class="SelectOption"/>
        <input type="text" v-model="param1.content" placeholder="검색어..." />
        <button type="button" @click="getSearchList">검색</button>
      </div>
    </div>
    <table class="BoardContentBody">
      <thead>
        <tr>
          <th scope="col">글번호</th>
          <th scope="col">제목</th>
          <th scope="col">작성자</th>
          <th scope="col">조회수</th>
          <th scope="col">작성일</th>
        </tr>
      </thead>
      <tbody>
        <BoardListItem
          v-for="article in articles"
          :key="article.boardNo"
          :article="article"
        ></BoardListItem>
      </tbody>
    </table>
  </div>
  <VPageNavigation
    :current-page="currentPage"
    :total-page="totalPage"
    @pageChange="onPageChange"
  ></VPageNavigation>
</div>
</template>

<style scoped>
.BoardWrapper{
  /* background-color: pink; */
  width: 140vh;
  margin: 0 auto;
  padding: 0 10vh;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  text-align: center;
}

.BoardTitle{
  /* background-color: skyblue; */
}

.BoardTitle h1{
  margin: 0;
}

.BoardContent{
  /* background-color: orange; */
}

.BoardContentHeader{
  /* background-color: rgb(211, 211, 211); */
  display: flex;
  justify-content: space-between;
  height: 5vh;
  margin: 5vh 0;
}

.BoardWrite{
  width: 10vh;
}

.BoardWrite button{
  width: 10vh;
  height: 5vh;
  border-radius: 1vh;
  color: rgb(250, 250, 250);
}

.BoardSearch{
  /* background-color: rgb(61, 74, 80); */
  display: flex;
  width: 50vh;
  justify-content: space-between;
}

.BoardSearch .SelectOption{
  width: 15vh;
  height: 5vh;
  border-radius: 1vh;
  padding: 0 1vh;
  border: 0.3vh solid rgb(230, 230, 230);
  color: rgb(50, 50, 50);
  border-radius: 1vh;
}

.BoardSearch .SelectOption:focus{
  outline: none;
}

.BoardSearch input[type="text"]{
  background-color: rgb(218, 222, 224);
  font-family: 'Pretendard-Regular';
  font-size: 1.5vh;
  width: 45%;
  height: 5vh;
  padding: 0 1vh;
  border: none;
  border-radius: 1vh;
}
.BoardSearch input[type="text"]:focus {
  outline: none;
  /* background-color: rgb(190, 203, 209); */
}


.BoardSearch button{
  padding: 0 2vh;
  height: 5vh;
  border-radius: 1vh;
  color: rgb(250, 250, 250);

}

.BoardContentBody{
  /* background-color: pink; */
  width: 120vh;
  border-collapse: separate;
  border-spacing: 0 0.3vh;
}

.BoardContentBody thead{
  background-color: rgb(122, 133, 139);
  color: rgb(230, 230, 230);
  height: 5vh;
}

.BoardContentBody tbody td{
  background-color: rgb(184, 184, 184);
  height: 5vh;
}
</style>
