<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";



const router = useRouter();
const memberStore = useMemberStore();
const { isSignUpTrue, isSignUpIdCheck } = storeToRefs(memberStore);
const { userSignUp, userSignUpIdCheck } = memberStore;

const isSignUp = async() => {
  //isSignUpIdCheck.value : 아이디 중복 체크 확인 여부

  if (isSignUpIdCheck.value) {
    if (newUser.value.pwd === newUser.value.pwdCheck){
      await userSignUp(newUser.value);
      router.push("/");
    }
    else {
      alert("비밀번호를 재확인 해주세요.")
    }

    if (!isSignUpTrue.value){
    alert("회원 가입 중 오류 발생!")
    }
  }
  else {
    alert("아이디 중복확인을 해주세요.")
  }
}


const checkId = async () => {
  await userSignUpIdCheck(newUser.value.id);

  if (isSignUpIdCheck.value) {
    alert("사용 가능한 아이디 입니다.")
  }
  else {
    alert("이미 사용중인 아이디 입니다.")
  }
}

const onInputCheckID = function(){
  isSignUpIdCheck.value = false;
}

const newUser = ref({
  id: "",
  pwd: "",
  pwdCheck: "",
  name: "",
  email: "",
})
</script>

<template>
  <div class="Wrapper">
    <div class="JoinArea">
      <div class="JoinContent">
        <div class="JoinId">
          <input
            type="text"
            v-model="newUser.id"
            placeholder="아이디"
            @input="onInputCheckID"
          />
          <button type="button" @click="checkId">중복 확인</button>
        </div>

        <div class="JoinPwd">
          <input
            type="password"
            v-model="newUser.pwd"
            placeholder="비밀번호"
          />
        </div>

        <div class="JoinPwdCheck">
          <input
            type="password"
            v-model="newUser.pwdCheck"
            placeholder="비밀번호 확인"
          />
        </div>

        <div class="JoinName">
          <input
            type="text"
            v-model="newUser.name"
            placeholder="이름"
          />
        </div>

        <div class="JoinEmail">
          <input
            type="email"
            v-model="newUser.email"
            placeholder="이메일"
          />
        </div>

        <button type="button" @click="isSignUp" class="BtnJoin">회원가입</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.Wrapper{
  /* background-color: gray; */
  height: 75vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.JoinArea{
  background-color: rgb(240, 240, 240);
  height: 70vh;
  width: 60vh;
  padding: 2vh;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  text-align: center;
  border-radius: 2vh;
	box-shadow: 2px 5px 10px 2px rgb(230, 230, 230);
}

.JoinContent{
  /* background-color: pink; */
  height: 55vh;
}

.JoinContent div{
  margin-bottom: 3vh;
}

input, button{
  font-family: 'Pretendard-Regular';
  border: none;
  padding: 0;
}

button{
  background-color: rgba(122, 133, 139, 0.5);
  color: rgb(241, 241, 240);
  cursor: pointer;
  font-size: 1.7vh;
}

button:hover{
  background-color: rgba(122, 133, 139, 0.7);
  transition: 0.4s;
}

.JoinContent input{
  padding: 0 1vh;
  box-sizing: border-box;
}

.JoinContent input:focus{
  outline: none;
}

.JoinContent input[type="text"], input[type="password"], input[type="email"]{
  border: 0.2vh solid rgb(245, 245, 245);
  width: 80%;
  height: 6vh;
  border-radius: 1vh;
  font-size: 1.5vh;
}

.JoinId{
  width: 80%;
  display: flex;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}

.JoinId input{
  flex: 4;
  margin-right: 1vh;
}

.JoinId button{
  flex: 1;
  border-radius: 1vh;
  margin: 0.1vh 0;
}

.BtnJoin{
	box-shadow: 2px 5px 10px 2px rgb(240, 240, 240);
  border: none;
  width: 80%;
  height: 7vh;
  border-radius: 1vh;
  margin: 3vh 0 0 0;
  font-size: 2.5vh;
}
</style>
