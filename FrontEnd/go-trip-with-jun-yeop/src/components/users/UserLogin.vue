<script setup>
import { onMounted, ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useHeaderMenuStore } from "@/stores/headerMenu";


const router = useRouter();

const memberStore = useMemberStore();
const { isLogin, isLoginError } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { changeHeaderMenuState } = useHeaderMenuStore();

const login = async () => {
  console.log(savedId.value);

  // 아이디 저장 체크되어 있다면
  if (savedId.value){
    setCookie("id", loginUser.value.id, 1);
  }
  else{
    document.cookie = "id" + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
  }
  
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  if (isLogin.value) {
    await getUserInfo(token);
    changeHeaderMenuState();
    router.push("/");
  }

  if (isLoginError.value) {
    alert("아이디와 패스워드를 확인해주세요!")
  }
};


const setCookie = (key, value, expiredDays) => {
  let today = new Date();
  today.setDate(today.getDate() + expiredDays);
  document.cookie =
    key +
    '=' +
    JSON.stringify(value) +
    '; path=/; expires=' +
    today.toGMTString() +
    ';';
};

const getCookie = (key) => {
  const cookies = document.cookie.split(`; `).map((el) => el.split('='));
  let getItem = [];

  for (let i = 0; i < cookies.length; i++) {
    if (cookies[i][0] === key) {
      getItem.push(cookies[i][1]);
      break;
    }
  }

  if (getItem.length > 0) {
    return JSON.parse(getItem[0]);
  }
};

onMounted(() => {
  loginUser.value.id = getCookie("id");
  if(getCookie("id")) savedId.value = true;
})


const loginUser = ref({
  id: "",
  pwd: "",
});
const savedId = ref(false)



</script>

<template>
<div class="Wrapper">
  <div class="LoginArea">
    <!-- <div class="LoginTitle"><h1>로그인</h1></div> -->
    <div class="LoginContent">
      <div class="LoginId">
        <input
        type="text"
        v-model="loginUser.id"
        placeholder="아이디"
        />
      </div>
      <div class="LoginPwd">
        <input
        type="password"
        v-model="loginUser.pwd"
        @keyup.enter="login"
        placeholder="비밀번호"
        />
      </div>
      <div class="LoginOptional">
        <div class="LoginFormIdSave">
          <input type="checkbox" v-model="savedId"/>
          <div class="LoginIdSaveLabel"> 아이디 저장 </div>
        </div>
        <router-link :to="{ name: 'user-join' }" class="Join">회원가입</router-link>
      </div>
      <button type="button" @click="login" class="BtnLogin">로그인</button>
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

.LoginArea{
  background-color: rgb(240, 240, 240);
  height: 50vh;
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

.LoginContent{
  /* background-color: rgb(146, 107, 107); */
  height: 35vh;
}

.LoginContent div{
  margin-bottom: 3vh;
}

.LoginContent input[type="text"], input[type="password"]{
  width: 80%;
  height: 6vh;
  margin-bottom: 1vh;
}

.LoginOptional{
  /* background-color: beige; */
  display: flex;
  height: 3vh;
  width: 80%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  font-size: 1.7vh;
}

.LoginFormIdSave{
  display: flex;
}

.LoginFormIdSave input{
  width: 2vh;
  height: 2vh;
}

.LoginFormIdSave input:focus{
  outline: none;
}

.LoginFormIdSave input:checked{
  accent-color: rgb(122, 133, 139);
  border: none;
}

.LoginIdSaveLabel{
  margin: 0 0 0 1vh;
  line-height: 3vh;
}

.Join{
  margin: 0 1vh 0 0;
  line-height: 3vh;
}

.BtnLogin{
  background-color: rgba(122, 133, 139, 0.5);
	box-shadow: 2px 5px 10px 2px rgb(240, 240, 240);
  color: rgb(240, 240, 240);
  border: none;
  width: 80%;
  height: 7vh;
  border-radius: 1vh;
  margin: 2vh 0 0 0;
  cursor: pointer;
  font-size: 2.5vh;
}

.BtnLogin:hover{
  background-color: rgba(122, 133, 139, 0.7);
  transition: 0.4s;
}

</style>
