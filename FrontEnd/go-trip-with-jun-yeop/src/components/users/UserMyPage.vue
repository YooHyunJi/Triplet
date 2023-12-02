<script setup>
// import { onMounted, ref } from 'vue';
// import { storeToRefs } from "pinia";
// import { useRouter } from 'vue-router';
// import { useMemberStore } from "@/stores/member";
import TheMyPageNavbar from "../layout/TheMyPageNavbar.vue";

</script>

<template>
  <div class="Wrapper">
    <TheMyPageNavbar/>
    <RouterView/>
</div>
</template>

<style scoped>
.Wrapper{
  margin: 10vh auto;
  display: flex;
  box-sizing: border-box;
  padding: 0 5vh;
}

.MyInfo{
  width: 180vh;
  box-sizing: border-box;
  margin-left: 10vh;
  display: flex;
  flex-direction: column;
}

.MyPageTitle{
  line-height: 5vh;
  margin-bottom: 3vh;
}

.MyPageContent{
  background-color: rgb(250, 250, 250);
  width: 100vh;
  padding: 5vh;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  border-radius: 2vh;
	box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
}

.MyPageInfo{
  display: flex;
  margin-bottom: 3vh;
}

.MyInfoLeft{
  display: flex;
  flex-direction: column;
}

.MyInfoImgPre{
  background-color: rgb(174, 174, 174);
  width: 30vh;
  height: 30vh;
  box-sizing: border-box;
  border-radius: 50%;
  overflow: hidden;
}

.MyInfoImgPre img{
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.FileSelect{
  display: flex;
  height: 4vh;
  margin-top: 6vh;
}

.FileSelect .upload-name {
  width: 20vh;
  padding: 0 1vh;
  box-sizing: border-box;
  border: 1px solid #dddddd;
  color: #999999;
  border-radius: 1vh;
}

.FileSelect label{
  flex: 1;
  display: inline-block;
  width: 5vh;
  font-size: 1.5vh;
  line-height: 4vh;
  color: #fff;
  background-color: rgb(175, 184, 188);
  cursor: pointer;
  border-radius: 1vh;
  margin-left: 2vh;
  padding: 0 1vh;
  text-align: center;
}

.FileSelect label:hover{
  background-color: rgba(122, 133, 139, 0.7);
  transition: 0.4s;
}

.FileSelect input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

.MyInfoRight{
  width: 60vh;
  padding: 0 0 0 5vh;
  box-sizing: border-box;
}

.MyInfoRight div{
  line-height: 5vh;
  margin-bottom: 2vh;
  display: flex;
}

.MyInfoRight label{
  display: inline-block;
  width: 20vh;
  line-height: 5vh;
}

.MyInfoRight input{
  width: 42vh;
}

.MyInfoNickName button{
  background-color: rgba(122, 133, 139, 0.5);
  color: rgb(240, 240, 240);
  margin-left: 3vh;
  width: 10vh;
	box-shadow: 2px 5px 10px 2px rgb(240, 240, 240);
  border: none;
  border-radius: 1vh;
  cursor: pointer;
}

.MyInfoNickName button:hover{
  background-color: rgba(122, 133, 139, 0.7);
  transition: 0.4s;
}

.BtnMyInfoModify{
  background-color: rgba(122, 133, 139, 0.5);
	box-shadow: 2px 5px 10px 2px rgb(240, 240, 240);
  color: rgb(240, 240, 240);
  border: none;
  width: 100%;
  height: 7vh;
  border-radius: 1vh;
  margin: 2vh 0 0 0;
  cursor: pointer;
  font-size: 2.5vh;
}

.BtnMyInfoModify:hover{
  background-color: rgba(122, 133, 139, 0.7);
  transition: 0.4s;
}

</style>
