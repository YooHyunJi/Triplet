import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "../views/TheMainView.vue";
import TheAttractionView from "../views/TheAttractionView.vue";
import ThePlanView from "../views/ThePlanView.vue";
import TheSavedPlanView from "../views/TheSavedPlanView.vue";
import TheChattingView from "../views/TheChattingView.vue";

import { useMemberStore } from "@/stores/member";
import { useHeaderMenuStore } from "@/stores/headerMenu";

const onlyAuthUser = async (to, from, next) => {

  await checkLoginStatus();
  const memberStore = useMemberStore();
  let token = sessionStorage.getItem("accessToken");

  if (memberStore.userInfo != null && token) {
    await memberStore.getUserInfo(token);
  }

  if (!memberStore.isValidToken || memberStore.userInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "user-login" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const checkLoginStatus = async () => {
  const memberStore = useMemberStore();
  const headerMenuStore = useHeaderMenuStore();
  console.log("checkLoginStatus");
  let token = sessionStorage.getItem("accessToken");
  if (memberStore.userInfo == null && token) { // 토큰은 있는데, pinia 는 날라간 상태
    await memberStore.getUserInfo(token);
    headerMenuStore.changeHeaderMenuState();
    console.log(memberStore.isValidToken);
    memberStore.isLogin = true;
    memberStore.isLoginError = false;
    memberStore.isValidToken = true;
  }
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      // component: TheDraggableView,
      beforeEnter :checkLoginStatus,
      component: TheMainView,
    },

    {
      path: "/user",
      name: "user",
      component: () => import("@/views/TheUserView.vue"),
      beforeEnter :checkLoginStatus,
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/users/UserLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/users/UserRegister.vue"),
        },
        {
          path: "mypage",
          name: "user-mypage",
          // beforeEnter: onlyAuthUser, // interceptor 역할
          component: () => import("@/components/users/UserMyPage.vue"),
          children: [
            {
              path: "/modify",
              name: "user-modify",
              component: () => import("@/components/users/UserMyPageModify.vue"),
            },
            {
              path: "/mytrip",
              name: "user-mytrip",
              component: () => import("@/components/users/UserMyPageTrip.vue"),
            },
            {
              path: "/mytripattr",
              name: "user-mytripattr",
              component: () => import("@/components/users/UserMyPageTripAttraction.vue"),
            },
            {
              path: "/myboard",
              name: "user-myboard",
              component: () => import("@/components/users/UserMyPageTripBoard.vue"),
            },
          ],
        },
      ],
    },

    {
      path: "/board",
      name: "board",
      component: () => import("../views/TheBoardView.vue"),
      beforeEnter :checkLoginStatus,
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          beforeEnter: onlyAuthUser,  // interceptor 역할
          component: () => import("@/components/boards/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/BoardModify.vue"),
        },
      ],
    },

    {
      path: "/attractions",
      name: "attractions",
      component: TheAttractionView,
      beforeEnter: checkLoginStatus,
    },

    {
      path: "/plan",
      name: "plan",
      component: ThePlanView,
      beforeEnter: [checkLoginStatus, onlyAuthUser],
    },

    {
      path: "/plan/:userId/:planTitle",
      name: "savedPlan",
      component: TheSavedPlanView,
      beforeEnter: [checkLoginStatus, onlyAuthUser],
    },

    {
      path: "/chatting",
      name: "chatting",
      component: TheChattingView,
      beforeEnter: [checkLoginStatus, onlyAuthUser],
    },
  ],
});

export default router;
