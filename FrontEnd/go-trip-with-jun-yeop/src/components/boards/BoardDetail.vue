<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  detailArticle, deleteArticle, listComment, writeComment,
  cntBoardLikes, statusLikes, addLikes, delLikes } from "@/api/board";
import { jwtDecode } from "jwt-decode";
import { useMemberStore } from "@/stores/member"
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
import BoardComment from "./item/BoardComment.vue";
import { httpStatusCode } from "@/util/http-status";

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;

const article = ref({});
const comments = ref([]);
const isMine = ref(false);
const newComment = ref("");
const isLike = ref(false);
const cntLikes = ref(0);
const boardInfo = ref({
  id: userInfo.value.id,
  boardNo : articleno
})
onMounted(() => {
  getArticle();
  getComment();
  getStatusLikes();
  getCntBoardLikes();
});

const boardAddLikes = () => {
  addLikes(
    boardInfo.value,
    (response) => {
      // if (response.status === httpStatusCode.OK) {
      //   cntLikes.value++;
      // }
      // cntLikes.value++;
    },
    (error) => {
      console.log(error)
    }
  )
}

const boardDelLikes = () => {
  delLikes(
    boardInfo.value,
    (response) => {
      // if (response.status === httpStatusCode.OK) {
      //   cntLikes.value--;
      // }
      // cntLikes.value--;
    },
    (error) => {
      console.log(error)
    }
  )
}

const getStatusLikes = () => {
  statusLikes(
    boardInfo.value,
    (response) => {
      if (response.status === httpStatusCode.OK)
          isLike.value = true;
      else 
        isLike.value = false;
      // console.log(isLike.value);
    },
    (error) => {
      console.log(error)
    }
  )
}

const getCntBoardLikes = () => {
  cntBoardLikes(
    boardInfo.value,
    (response) => {
      // console.log("data", response.data);
      cntLikes.value = response.data;
    },
    (error) => {
      console.log(error)
    }
  )
}

const getArticle = () => {
  // const { articleno } = route.params;
  // console.log(articleno + "번글 얻으러 가자!!!");
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  
  detailArticle(
    articleno,
    ({ data }) => {
      article.value = data;
      if(data.id === decodeToken.id){
        isMine.value = true;
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

const getComment = () => { 
  // console.log(articleno);
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);

  listComment(
    articleno,
    ({ data }) => {
      data.forEach((d) => {
        if(d.id===decodeToken.id){
          d.isMyComment = true;
        }
        else{
          d.isMyComment = false;
        }
      })
      comments.value = data;
    },
    (error) => {
      console.log(error);
    }
  )
}

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
  deleteArticle(
    articleno,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.log(error);
    }
  );
}

const likeArticle = () => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  isLike.value = !isLike.value;
  if (isLike.value){
    boardAddLikes();
    cntLikes.value++;
  }
  else{
    boardDelLikes();
    cntLikes.value--;
  }
}

const addComment = () => {
  if(!newComment.value || newComment.value.replace(/^\s+|\s+$/g, '' ) == ""){
    alert("댓글 내용을 입력해주세요!");
    return;
  }
  
  const co = {
    boardNo: parseInt(articleno),
    id : userInfo.value.id,
    content :newComment.value,
  }
  writeComment(co, (res) => {
    newComment.value = "";
    getComment();
  }, (error) => { 
    console.log(error);
  })
}


</script>

<template>
<div class="BoardWrapper">
  <div class="BoardContent">
    <div class="ArticleTitle">
      <h1>{{ article.boardNo }}. {{ article.subject }}</h1>
      <!-- <h1>{{ cntLikes }}. {{ isLike }}</h1> -->
    </div>
    <div class="ArticleDesc">
      <span class="ArticleId">{{ route.query.nick }}</span>
      <span class="ArticleDate">{{ article.createDate }}</span>
      <span class="ArticleHit">조회수 : {{ article.hit }}</span>
    </div>
    <div class="ArticleContent">{{ article.content }}</div>
    <div class="Btns">
      <!-- <img src="src/assets/heart_empty.png" alt=""> -->
      <button type="button" @click="likeArticle" class="BtnLike" v-show="isLike"><div class="HeartArea"><img src="/public/heart_full.png"></div><p>{{ cntLikes }}</p></button>
      <button type="button" @click="likeArticle" class="BtnLike" v-show="!isLike"><div class="HeartArea"><img src="/public/heart_empty.png"></div><p>{{ cntLikes }}</p></button>
      <button type="button" @click="moveList">글 목록</button>
      <button v-if="isMine" type="button" @click="moveModify">수정</button>
      <button v-if="isMine" type="button" @click="onDeleteArticle">삭제</button>
    </div>
  </div>

  <div class="DivComment">
    <p class="CommentCnt">댓글 수 : {{ comments.length }}</p>
    <div class="CommentList">
      <BoardComment
        v-for="comment in comments"
        :key="comment.commentNo"
        :comment="comment"
        @reload-comment="getComment"
      >
      </BoardComment>
    </div>
    <div class="CommentWrite">
      <textarea  
      v-model="newComment"
      @keyup.enter="sendMessage"
      placeholder="Type your comment"
      id="newComment"></textarea>
      <button type="button" @click="addComment">게시</button>
    </div>
  </div>
</div>
</template>

<style scoped>
.BoardWrapper{
  width: 140vh;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
}

.BoardContent{
  background-color: rgb(240, 240, 240);
	box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
  border-radius: 2vh;
  /* margin-bottom: 3vh; */
  padding: 5vh 10vh;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  text-align: center;
}

.ArticleTitle{
  margin-bottom: 3vh;
  display: flex;
  flex-direction: column;
}

.ArticleTitle h1{
  margin: 0 0 1vh 0;
}

.ArticleDesc{
  width: 50vh;
  margin: 0 auto;
  display: flex;
  font-size: 1.7vh;
  justify-content: space-between
}

.ArticleContent{
  background-color: white;
	box-shadow: 2px 5px 10px 2px rgb(240, 240, 240);
  padding: 5vh;
  border-radius: 2vh;
  line-height: 4vh;
  margin: 5vh 0;
  /* outline: 0.3vh solid rgba(122, 133, 139, 0.5); */
  color: rgb(50, 50, 50);
}

.Btns{
  /* background-color: orange; */
  width: 100%;
  margin: 0 auto;
  display: flex;
  justify-content: flex-end;
}

.Btns button{
  padding: 0 1vh;
  height: 4vh;
  border-radius: 1vh;
  margin-left: 1vh;
}

.BtnLike{
  border: 0.3vh solid rgba(122, 133, 139, 0.5);
  background-color: rgb(250, 250, 250);
  width: auto !important;
  line-height: 3.2vh;
  padding: 0.1vh 1.2vh 0 1.2vh;
  /* background-color: rgb(252, 237, 237); */
  color: rgb(100, 100, 100) !important;
  display: flex;
  justify-content: space-between;
}

.BtnLike:hover{
  background-color: rgba(122, 133, 139, 0.5);
  color: rgb(240, 240, 240) !important;
  transition: 0.4s;
}

.BtnLike .HeartArea{
  /* background-color: red; */
  width: 2.2vh;
  height: 2.3vh;
  box-sizing: border-box;
  margin: 0.6vh 0.5vh 0 0;
}

.BtnLike .HeartArea img{
  width: 100%;
  height: 100%;
}

.BtnLike p{
  /* background-color: orange; */
  margin: 0;
  min-width: 2vh;
  text-align: right;
  margin-top: 0.1vh;
}

.DivComment{
  /* background-color: orange; */
  margin-top: 5vh;
  text-align: left;
}

.CommentWrite{
  background-color: rgb(240, 240, 240);
	box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
  padding: 3vh;
  box-sizing: border-box;
  border-radius: 1vh;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.CommentWrite textarea{
  height: 20vh;
  padding: 3vh;
  font-size: 2.5vh;
  resize: none;
  margin-bottom: 2vh;
}

.CommentWrite textarea:focus{
  border: 0.4vh solid rgba(122, 133, 139, 0.5);  
}

.CommentWrite button{
  background-color: rgba(122, 133, 139, 0.5);
	box-shadow: 2px 5px 10px 2px rgb(240, 240, 230);
  color: rgb(240, 240, 240);
  border: none;
  width: 8vh;
  height: 5vh;
  border-radius: 1vh;
  cursor: pointer;
  font-size: 2.2vh;
  margin-left: auto;
}

.CommentWrite button:hover{
  background-color: rgba(122, 133, 139, 0.7);
  transition: 0.4s;
}
</style>