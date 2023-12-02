<script setup>
import { onMounted, ref } from 'vue';
import { storeToRefs } from "pinia";
// import { useRouter } from 'vue-router';
import { useMemberStore } from "@/stores/member";

// const router = useRouter();

const memberStore = useMemberStore();
const { userInfo, isChangeNickName, curImageFile, isCorrectPwd ,isModifyNickName, isModifyPwd} = storeToRefs(memberStore);
const { userNickNameCheck, userProfileImage, userCheckPwd, userModifyNickName, userModifyPwd } = memberStore;

const myPageInfo = userInfo.value;
const pwdNewCheck = ref();
const pwdNew = ref();
const pwdInput = ref();
const newNickName = ref();
newNickName.value = myPageInfo.nickName;
const profileImage = ref();
profileImage.value = myPageInfo.profile;


const checkNickName = async () => {
  console.log(newNickName.value);
  if (newNickName.value == null || newNickName.value == "") {
    alert("닉네임을 입력해주세요.");
    return;
  }
  await userNickNameCheck(newNickName.value);
  if (isChangeNickName.value) {
    alert("사용가능한 닉네임 입니다.")
  }
  else if (newNickName.value == myPageInfo.nickName) {
    alert("기존에 사용한 닉네임과 같은 닉네임 입니다.")
    isChangeNickName.value = true;
  }
  else {
    alert("이미 사용 중인 닉네임 입니다.")
  }
}
const onInputCheckNickName = function(){
  isChangeNickName.value = false;
}

const changeProfileImage = async () => {
  const newProfile = document.getElementById("fileUpload");

  let formData = new FormData();
  formData.append('file', newProfile.files[0]);
  formData.append('id', '?');
  await userProfileImage(formData);
  console.log(curImageFile.value);
  profileImage.value = curImageFile.value;
  // myPageInfo.profile = curImageFile.value;

}

onMounted(() => {
  const fileUploadName = document.querySelector("#fileUpload");
  fileUploadName.addEventListener("change", () => {
    const locationName = document.querySelector("#fileUpload").value;
    const fileName = locationName.split('\\').reverse()[0];
    document.querySelector("#upload-name").value = fileName;
  })
})

const modify = async() => {

  const newProfile = document.getElementById("fileUpload");
  if (nullCheck(newProfile.files[0])){
    let formData = new FormData();
    formData.append('file', newProfile.files[0]);
    formData.append('id', myPageInfo.id);
    await userProfileImage(formData);
    profileImage.value = curImageFile.value;
    myPageInfo.profile = curImageFile.value;
  }

  if (newNickName.value == null || newNickName.value == ""
      || newNickName.value == myPageInfo.nickName ) {
    return;
  }
  if (isChangeNickName.value) {
    userModify.value.id = myPageInfo.id;
    userModify.value.nickName = newNickName.value;
    console.log(userModify.value);
    await userModifyNickName(userModify.value);
    if (isModifyNickName.value){
      alert("닉네임 변경 완료");
      myPageInfo.nickName = userInfo.value.nickName = newNickName.value;
      
    }
    else
      alert("닉네임 변경 시 오류 발생");
  }
  else{
    alert("닉네임 중복 검사를 해주세요.");
  }
}

const BtnMyPwdModify = async() => {
  userModify.value.id = myPageInfo.id;
  userModify.value.pwd = pwdInput.value;
  await userCheckPwd(userModify);
  if (isCorrectPwd.value){
    alert("비밀번호를 변경할 수 있습니다.")
    if (pwdNew.value === pwdNewCheck.value){
      await userModifyPwd(myPageInfo.id, pwdNew.value)
      if (isModifyPwd.value)
        alert("비밀번호 변경 완료!");
      else
        alert("비밀번호 변경 도중 에러 발생.");
    }
    else{
      alert("새 비밀번호를 동일하게 입력해주세요.")
    }
  }
  else{
    alert("기존 비밀번호와 다른 비밀번호 입니다.")
  }
}

function nullCheck(obj, defaultStr){
  if(typeof obj == "undefined" || obj == null || obj == "")
    return false;
  
  return true;
}

const userModify = ref({
  id : "",
  pwd : "",
  nickName : ""
})

onMounted(() => {
  const fileUploadName = document.querySelector("#fileUpload");
  fileUploadName.addEventListener("change", () => {
    const locationName = document.querySelector("#fileUpload").value;
    const fileName = locationName.split('\\').reverse()[0];
    document.querySelector("#upload-name").value = fileName;
  })
})

</script>

<template>
<div class="MyPageWrapper">
  <div class="MyInfo">
    <div class="MyPageTitle"><h1>내 정보</h1></div>
    <div class="MyPageContent">
      <div class="MyPageInfo">

        <div class="MyInfoLeft">
          <div class="MyInfoImgPre">
            <img :src="'data:image/gif;base64,' + profileImage"/>
          </div>
          <div class="FileSelect">
            <input id="upload-name" class="upload-name" value="첨부파일" placeholder="첨부파일">
            <label for="fileUpload">파일찾기</label> 
            <input 
              type="file"
              id ="fileUpload"
              @change = "changeProfileImage"
              accept=".png">
          </div>
        </div>
        
        <div class="MyInfoRight">
          <div class="MyInfoId">
            <label for="id">아이디</label>
            {{ myPageInfo.id }}
          </div>
    
          <!-- <div class="MyInfoPwdPre">
            <label for="pwd"> 기존 비밀번호</label>
            <input
              type="password"
              v-model="pwdInput"
              placeholder="비밀번호를 입력해주세요."
            />
          </div>
    
          <div class="MyInfoPwdNew">
            <label for="pwdCheck">새 비밀번호 입력</label>
            <input
              type="password"
              v-model="pwdNew"
              placeholder="새 비밀번호를 입력해주세요."
            />
          </div>
    
          <div class="MyInfoPwdNewCheck">
            <label for="pwdCheck">새 비밀번호 확인</label>
            <input
              type="password"
              v-model="pwdNewCheck"
              placeholder="새 비밀번호를 다시 입력해주세요."
            />
          </div> -->
    
          <div class="MyInfoName">
            <label for="name">이름</label>
            {{ myPageInfo.name }}
          </div>
    
          <div class="MyInfoEmail">
            <label for="id">이메일</label>
            {{ myPageInfo.email }}
          </div>
    
          <div class="MyInfoNickName">
            <label for="nickName">닉네임</label>
            <input
              type="text"
              v-model="newNickName"
              :placeholder="myPageInfo.nickName"
              @input="onInputCheckNickName"
            />
            <button type="button" @click="checkNickName">중복 확인</button>
          </div>
        </div>
      </div>

      <button type="button" @click="modify" class="BtnMyInfoModify">수정</button>
    </div>
  </div>
</div>
</template>

<style scoped>
.MyPageWrapper{
  /* background-color: skyblue; */
  width: 160vh;
  padding: 0 5vh;
  box-sizing: border-box;
  display: flex;
  /* text-align: ; */
}

.MyInfo{
  box-sizing: border-box;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
}

.MyPageTitle h1{
  margin: 0;
  margin-bottom: 3vh;
  text-align: center;
}

.MyPageContent{
  background-color: rgb(240, 240, 240);
  width: 120vh;
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
  /* background-color: pink; */
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
  /* background-color: green; */
  width: 80vh;
  padding: 0 0 0 7vh;
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
