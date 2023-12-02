<script setup>
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const route = useRoute();
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const props = defineProps({ type: String });

const isUseId = ref(false);

const article = ref({
  boardNo: 0,
  id: "",
  content: "",
  subject: "",
  content: "",
  img: 0,
  createDate: "",
});

if (props.type === "modify") {
  let { articleno } = route.params;
  getModifyArticle(
    articleno,
    ({ data }) => {
      article.value = data;
      isUseId.value = true;
    },
    (error) => {
      console.log(error);
    }
  );
  isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  registArticle(
    article.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.log(error)
  );
}

function updateArticle() {
  modifyArticle(
    article.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>

<template>
  <div class="BoardWriteWrapper">
    <form @submit.prevent="onSubmit">
      <div class="ArticleTitle">
        <input type="text" class="form-control" v-model="article.subject" placeholder="제목을 입력해주세요 !" />
      </div>
      <div class="ArticleTextArea">
        <textarea class="form-control" v-model="article.content" rows="10"></textarea>
      </div>
      <div class="Btns">
        <button type="submit" v-if="type === 'regist'" class="BtnCommit">
        작성
        </button>
        <button type="submit" v-else class="BtnCommit">수정</button>
        <button type="button" @click="moveList" class="BtnList">
          글 목록
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.BoardWriteWrapper{
  background-color: rgb(240, 240, 240);
	box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
  border-radius: 2vh;
  width: 140vh;
  margin: 0 auto;
  padding: 5vh 10vh;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  text-align: center;
}
.ArticleTitle{
  /* background-color: skyblue; */
}

.ArticleTitle input{
  width: 100%;
  height: 5vh;
  text-align: center;
  font-size: 2vh;
}

.ArticleTextArea textarea{
  /* background-color: oran; */
  width: 100%;
  height: 50vh;
  resize: none;
  padding: 3vh;
  font-size: 2vh;
  margin: 3vh 0;
}

.ArticleTextArea textarea:focus{
  outline: 0.3vh solid rgba(122, 133, 139, 0.5);
  color: rgb(50, 50, 50);
}

.Btns{
  /* background-color: orange; */
  /* width: 30vh; */
  margin: 0 auto;
  display: flex;
  justify-content: flex-end;
}

.Btns button{
  background-color: rgba(122, 133, 139, 0.5);
	box-shadow: 2px 5px 10px 2px rgb(240, 240, 240);
  color: rgb(240, 240, 240);
  border: none;
  padding: 0 1vh;
  height: 4vh;
  border-radius: 1vh;
  cursor: pointer;
  font-size: 2vh;
  margin-left: 1vh;
}

.Btns button:hover{
  background-color: rgba(122, 133, 139, 0.7);
  transition: 0.4s;
}
</style>
