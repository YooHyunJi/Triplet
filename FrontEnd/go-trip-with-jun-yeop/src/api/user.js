import { localAxios, fileLoadAxios } from "@/util/http-commons";

const local = localAxios();
const fileload = fileLoadAxios();

async function userConfirm(param, success, fail) {
  await local.post(`/user/login`, param).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function signUp(param, success, fail) {
  await local.post(`/user/signup`, param).then(success).catch(fail);
}

async function signUpIdCheck(param, success, fail) {
  await local.post(`/user/check/id`, param).then(success).catch(fail);
}

async function nickNameCheck(param, success, fail) {
  await local.post(`/user/check/nickname`, param).then(success).catch(fail);
}

async function profileImage(formData, success, fail) {
  await fileload.post(`/user/file/upload`, formData).then(success).catch(fail);
}

async function checkPwd(param, success, fail) {
  await local.post(`/user/check/pwd`, param).then(success).catch(fail);
}

async function modifyNickName(param, success, fail) {
  await local.post(`/user/modify/nickname`, param).then(success).catch(fail);
}

async function modifyPwd(id, newPwd, success, fail) {
  await local.post(`/user/modify/pwd`, id, newPwd).then(success).catch(fail);
}
async function getImage(id, success, fail) {
  await local.get(`/user/file/download/${id}`).then(success).catch(fail);
}

async function getLikeAttr(id, success, fail) {
  await local.get(`/attr/likelist/${id}`).then(success).catch(fail);
}
async function getLikeBoard(id, success, fail) {
  await local.get(`/board/likelist/${id}`).then(success).catch(fail);
}

export { userConfirm, findById, tokenRegeneration, logout
  , signUp, signUpIdCheck, nickNameCheck, profileImage, checkPwd
  , modifyNickName, modifyPwd, getImage, getLikeAttr, getLikeBoard};
