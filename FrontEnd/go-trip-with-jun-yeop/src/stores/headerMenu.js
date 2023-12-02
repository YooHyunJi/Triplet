import { ref } from "vue";
import { defineStore } from "pinia";

export const useHeaderMenuStore = defineStore("headerMenuStore", () => {
  const headerMenuList = ref([
    { name: "회원가입", show: true, routeName: "user-join" },
    { name: "로그인", show: true, routeName: "user-login" },
    { name: "내정보", show: false, routeName: "user-modify" },
    { name: "로그아웃", show: false, routeName: "user-logout" },
  ]);

  const changeHeaderMenuState = () => {
    headerMenuList.value = headerMenuList.value.map((item) => ({ ...item, show: !item.show }));
  };
  return {
    headerMenuList,
    changeHeaderMenuState,
  };
});
