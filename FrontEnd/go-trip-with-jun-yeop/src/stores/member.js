import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  signUp,
  signUpIdCheck,
  nickNameCheck,
  profileImage,
  checkPwd,
  modifyNickName,
  modifyPwd,
  getImage,
  getLikeAttr,
  getLikeBoard,
} from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore("memberStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref(null);
  const isValidToken = ref(false);
  const isSignUpTrue = ref(false);
  const isSignUpIdCheck = ref(false);
  const isChangeNickName = ref(false);
  const isSamePwd = ref(false);
  const curImageFile = ref();
  const getImageFile = ref();
  const isCorrectPwd = ref(false);
  const isModifyNickName = ref(false);
  const isModifyPwd = ref(false);
  const myLikeAttrList = ref([]);
  const myLikeBoardList = ref([]);

  const userLogin = async (loginUser) => {
    await userConfirm(
      loginUser,
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let { data } = response;
          let accessToken = data["access-token"];
          let refreshToken = data["refresh-token"];
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          sessionStorage.setItem("accessToken", accessToken);
          sessionStorage.setItem("refreshToken", refreshToken);
        } else {
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const getUserInfo = async (token) => {
    let decodeToken = jwtDecode(token);
    await findById(
      decodeToken.id,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          userInfo.value = response.data.userInfo;
        } else {
        }
      },
      async (error) => {
        console.error(
          "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
          error.response.status
        );
        isValidToken.value = false;
        await tokenRegenerate();
      }
    );
  };

  const tokenRegenerate = async () => {
    console.log(
      "토큰 재발급 >> 기존 토큰 정보 : {}",
      sessionStorage.getItem("accessToken")
    );
    await tokenRegeneration(
      JSON.stringify(userInfo.value),
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let accessToken = response.data["access-token"];
          sessionStorage.setItem("accessToken", accessToken);
          isValidToken.value = true;
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            userInfo.value.id,
            (response) => {
              if (response.status === httpStatusCode.OK) {
              } else {
              }
              alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
              isLogin.value = false;
              userInfo.value = null;
              isValidToken.value = false;
              router.push({ name: "user-login" });
            },
            (error) => {
              console.error(error);
              isLogin.value = false;
              userInfo.value = null;
            }
          );
        }
      }
    );
  };

  const userLogout = async (id) => {
    await logout(
      id,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false;
          userInfo.value = null;
          isValidToken.value = false;
          sessionStorage.removeItem("accessToken");
          sessionStorage.removeItem("refreshToken");
        } else {
          console.error("유저 정보 없음!!!!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const userSignUp = async (signUpUser) => {
    await signUp(
      signUpUser,
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          isSignUpTrue.value = true;
        } else {
          isSignUpTrue.value = false;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const userSignUpIdCheck = async (newId) => {
    await signUpIdCheck(
      newId,
      (response) => {
        console.log("userSignUpIdCheck start!!");
        if (response.status === httpStatusCode.OK) {
          console.log("signUpIdCheck success");
          isSignUpIdCheck.value = true;
        } else {
          isSignUpIdCheck.value = false;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const userNickNameCheck = async (newNickName) => {
    await nickNameCheck(
      newNickName,
      (response) => {
        console.log("userNickNameCheck start!!");
        if (response.status === httpStatusCode.OK) {
          console.log("isChangeNickName success");
          isChangeNickName.value = true;
        } else {
          isChangeNickName.value = false;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const userProfileImage = async (formData) => {
    await profileImage(
      formData,
      (response) => {
        console.log("userProfileImage start!!");
        if (response.status === httpStatusCode.OK) {
          let { data } = response;
          // console.log("data", data);
          // let accessToken = data["access-token"];
          curImageFile.value = data["new-file"];
          console.log("success!!");
        } else {
          console.log("false!!");
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const userCheckPwd = async (user) => {
    await checkPwd(
      user,
      (response) => {
        console.log("userCheckPwd start!!");
        if (response.status == httpStatusCode.OK) isCorrectPwd.value = true;
        else isCorrectPwd.value = false;
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const userModifyNickName = async (userModify) => {
    await modifyNickName(
      userModify,
      (response) => {
        console.log("userModifyNickName start!");
        if (response.status == httpStatusCode.CREATE)
          isModifyNickName.value = true;
        else isModifyNickName.value = false;
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const userModifyPwd = async (id, newPwd) => {
    await modifyPwd(
      id,
      newPwd,
      (response) => {
        console.log("userModifyPwd start!");
        if (response.status == httpStatusCode.CREATE) isModifyPwd.value = true;
        else isModifyPwd.value = false;
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const userGetImage = async (id) => {
    await getImage(
      id,
      (response) => {
        console.log(response);
        if (response.status == httpStatusCode.OK) {
          let { data } = response;
          getImageFile.value = data["profile"];
          console.log("userGetImage");
        }
      },
      (error) => {
        console.log(error);
      }
    )
  }

  const userGetLikeAttr = async (id) => {
    await getLikeAttr(
      id,
      (response) => {
        if (response.status == httpStatusCode.OK) {
          let { data } = response;
          myLikeAttrList.value = data["like-list"];
        }
        else {
          myLikeAttrList.value = "";
        }

      },
      (error) => {
        console.log(error);
      }
    )
  }

  const userGetLikeBoard = async (id) => {
    await getLikeBoard(
      id,
      (response) => {
        console.log(response);
        if (response.status == httpStatusCode.OK) {
          let { data } = response;
          myLikeBoardList.value = data["like-list"];
          console.log(myLikeBoardList.value);
        }
        else {
          myLikeBoardList.value = "";
        }
      },
      (error) => {
        console.log(error);
      }
    )
  }
  return {
    isLogin,
    isLoginError,
    userInfo,
    isValidToken,
    isSignUpTrue,
    isSignUpIdCheck,
    isChangeNickName,
    curImageFile,
    getImageFile,
    isCorrectPwd,
    isModifyNickName,
    isModifyPwd,
    myLikeAttrList,
    myLikeBoardList,
    userLogin,
    getUserInfo,
    tokenRegenerate,
    userLogout,
    userSignUp,
    userSignUpIdCheck,
    userNickNameCheck,
    userProfileImage,
    userCheckPwd,
    userModifyNickName,
    userModifyPwd,
    userGetImage,
    userGetLikeAttr,
    userGetLikeBoard,
  };
});
