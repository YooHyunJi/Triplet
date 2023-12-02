<script setup>
import { ref, onMounted } from "vue";
import { getNickname, modifyComment, deleteComment } from "@/api/board";


const props = defineProps({
  comment: Object,
});

const emit = defineEmits([
  "reloadComment"
]);

const nick = ref("");
const image = ref();

const commentModify = (commentNo) => {
  const comment = document.getElementById(commentNo);
  if(comment.hasAttribute("disabled")){
    comment.removeAttribute("disabled");
  }
  else{
    const co = {
      content: comment.value,
      commentNo: props.comment.commentNo,
    }
    modifyComment(
      co,
      (res) => { emit("reloadComment"); },
      (error) => { console.log(error); }
    );
    comment.setAttribute("disabled", true);
  }
}

const commentDelete = (commentNo) => {
  console.log(`${commentNo}번 댓글 삭제`);
  deleteComment(
    commentNo,
    (res) => { emit("reloadComment"); },
    (error) => { console.log(error);}
  );
}
const tmp = ref();
onMounted(async () => {
  
  const res = await getNickname(props.comment.id);
  // await (userGetImage(props.comment.id));
  nick.value = res.data.nickName;

  image.value = res.data.profile;
  if (image.value == null) {
    image.value = "iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAA1BMVEXNzs4XqC2+AAAASElEQVR4nO3BgQAAAADDoPlTX+AIVQEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwDcaiAAFXD1ujAAAAAElFTkSuQmCC";
  }
})
</script>

<template>
<div class="Comment">
  <div class="CommentDesc">
    <!-- 여기 닉네임 불러와서 다시체크 -->
    <div class="CommentUser">
      <span class="CommentProfile">
        <img :src="'data:image/gif;base64,' + image"></span>
      <span class="CommentWriter">{{nick}}</span>
    </div>
    <span class="CommentTime">{{ comment.createDate }}</span>
  </div>
  <div class="CommentContent">
    <textarea v-model="comment.content" :id="comment.commentNo" class="CommentContentTextarea" disabled></textarea>
    <!-- {{ comment.content }} -->
  </div>
  <div class="CommentBtns">
    <button v-if="comment.isMyComment" type="button" @click="commentModify(comment.commentNo)">수정</button>
    <button v-if="comment.isMyComment" type="button" @click="commentDelete(comment.commentNo)">삭제</button>
  </div>
</div>
</template>

<style scoped>
.Comment{
  background-color: rgb(240, 240, 240);
	box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
  padding: 3vh 3vh;
  box-sizing: border-box;
  border-radius: 1vh;
  margin-bottom: 3vh;
  display: flex;
  flex-direction: column;
}

.CommentDesc{
  /* background-color: pink; */
  height: 7vh;
  box-sizing: border-box;
  margin-bottom: 3vh;
  display: flex;
  justify-content: space-between;
}

.CommentUser{
  display: flex;
  line-height: 7vh;
}

.CommentProfile{
  /* background-color: red; */
  margin-right: 1.5vh;
  height: 7vh;
  width: 7vh;
}

.CommentProfile img{
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 50%;
}

.CommentWriter{
  font-size: 2.3vh;
}

.CommentTime{
  font-size: 1.7vh;
  color: rgb(145, 143, 140);
}

.CommentContent{
  /* background-color: rgb(250, 250, 250); */
  color: rgb(70, 69, 66);
  border-radius: 1vh;
  margin-bottom: 1vh;
}

.CommentContentTextarea{
  resize: none;
  padding: 3vh;
  margin: 0;
  font-size: 2vh;
  color: rgb(50, 50, 50);
}

.CommentBtns{
  /* background-color: orange; */
  width: 100%;
  margin: 0 auto;
  display: flex;
  justify-content: flex-end;
}

.CommentBtns button{
  background-color: rgba(122, 133, 139, 0.5);
	box-shadow: 2px 5px 10px 2px rgb(240, 240, 230);
  color: rgb(240, 240, 240);
  border: none;
  width: 7vh;
  height: 4vh;
  border-radius: 1vh;
  cursor: pointer;
  font-size: 2vh;
  margin-left: 1vh;
}

.CommentBtns button:hover{
  background-color: rgba(122, 133, 139, 0.7);
  transition: 0.4s;
}
</style>
