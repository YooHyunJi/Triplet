<script setup>
import { useHeaderMenuStore } from "@/stores/headerMenu";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const menuStore = useHeaderMenuStore();
const memberStore = useMemberStore();

const { headerMenuList } = storeToRefs(menuStore);
const { changeHeaderMenuState } = menuStore;
const { userInfo } = storeToRefs(memberStore);
const { userLogout } = memberStore;

const logout = async () => {
  await userLogout(userInfo.value.id);
  console.log("로그아웃!!!!");
  changeHeaderMenuState();
};
</script>

<template>
  <nav>
    <ul class="NavListLeft">
      <li><router-link :to="{ name: 'main' }" class="Logo">Triplet</router-link></li>
      <li><router-link :to="{ name: 'attractions' }">여행지 찾기</router-link></li>
      <li><router-link :to="{ name: 'plan' }">여행 계획</router-link></li>
      <li><router-link :to="{ name: 'board' }">게시판</router-link></li>
    </ul>
    <ul class="NavListRight">
      <template v-for="menu in headerMenuList" :key="menu.routeName">
        <!-- 세션 상태에 따라 menu.show===true일 경우 표시 -->
        <template v-if="menu.show">
          <template v-if="menu.routeName === 'user-logout'">
            <li><router-link to="/" @click.prevent="logout">{{ menu.name }}</router-link></li>
          </template>
          <template v-else>
            <li><router-link :to="{ name: menu.routeName }">{{ menu.name }}</router-link></li>
          </template>
        </template>
      </template>
    </ul>
  </nav>
</template>

<style scoped>
nav{
  /* background-color: rgb(189, 97, 92); */
  background-color: rgb(177, 58, 58);

  display: flex;
  flex-wrap: wrap;
  height: 8vh;
  width: 100%;
  padding: 0 3vh;
  box-sizing: border-box;
  margin-bottom: 5vh;
  box-shadow: 0.5vh 0.7vh 1vh 0.3vh rgba(0, 0, 0, 0.1);
}

nav a{
  color: rgb(230, 230, 230);
}

ul{
  display: flex;
  padding: 0;
  margin: 0;
}

.Logo{
  background-color: rgb(215, 209, 209);
  color:  rgb(177, 58, 58);
  padding: 0.5vh 1vh;
  border-radius: 1vh;;
  font-family: "twaysky";
  font-size: 4vh;
  line-height: 4vh;
  box-sizing: border-box;
  transition: 0.4s;
}

.Logo:hover {
  background-color: rgb(43, 38, 38);
  color:  rgb(177, 58, 58);
  transition: 0.4s;
}

.NavListLeft{
  /* flex: 5; */
  margin-right: auto;
  align-items: center;
}

.NavListRight{
  display: flex;
  justify-content: flex-end;
  /* flex: 5; */
  margin-left: auto;
}

li a{
  line-height: 8vh;
  margin: 0 1vh;
  color: rgb(210, 210, 210);
  transition: 0.5s;
}
li a:hover{
  color: rgb(240, 240, 240);
  transition: 0.5s;
}

@media screen and (max-width: 768px) {
  nav{
    flex-direction: column;
    align-items: flex-start;
    padding: 0;
    text-align: center;
    width: 100%;
    height: auto;
    margin-bottom: 5vh;
  }

  ul{
    display: flex;
    width: 100%;
    align-items: center;
    flex-direction: column;
  }

  li{
    line-height: 6vh;
    width: 100%;
    text-align: center;
  }

  .NavListLeft li:first-child {
    width: 100%;
    height: 7vh;
    line-height: 7vh;
    /* background-color: rgb(197, 216, 216); */
  }

  .NavListRight{
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: space-evenly;
  }

  .NavListRight li{
    /* background-color: aliceblue; */
    flex: 1;
  }
  
}
</style>
