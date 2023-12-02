import { localAxios } from "@/util/http-commons";
import { useMemberStore } from "@/stores/member"
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const local = localAxios();
function searchArticle(flag, success, fail) {
  const content = flag.content;
  const condition = flag.condition;
  const orderBy = flag.orderBy;
  const orderByDir = flag.orderByDir;
  local.get(`/board/search?content=${content}&condition=${condition}`).then(success).catch(fail)
}
function listArticle(param, success, fail) {
  local.get(`/board/list`, { params: param }).then(success).catch(fail);
}

function listArticlePg(param, success, fail) {
  local.get(`board/list/pg`, { params: param }).then(success).catch(fail);
}


function detailArticle(articleno, success, fail) {
  local.get(`/board/list/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  article.img = ""
  article.id = userInfo.value.id;
  local.post(`/board/write`, JSON.stringify(article), {
    headers: {
      'Content-Type': 'application/json',
    },
  }).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
  local.get(`/board/list/${articleno}`).then(success).catch(fail);
}
function modifyArticle(article, success, fail) {
  local.put(`/board/modify`, JSON.stringify(article)).then(success).catch(fail);
}
function deleteArticle(articleno, success, fail) {
  local.delete(`/board/delete/${articleno}`).then(success).catch(fail);
}
function writeComment(comment, success, fail) {
  console.log(comment);
  local.post(`/board/write/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function listComment(param, success, fail) {
  local.get(`/board/list/comment/${param}`).then(success).catch(fail);
}
function modifyComment(comment, success, fail) {
  local.put(`/board/modify/comment`, JSON.stringify(comment)).then(success).catch(fail);
}
function deleteComment(commentno, success, fail) {
  local.delete(`/board/delete/comment/${commentno}`).then(success).catch(fail);
}
function getNickname(id) {
  const result = local.post(`/user/find/nickname`, id);
  return result;
}

function cntBoardLikes(param, success, fail) {
  local.get(`/board/info/cnt/${param.boardNo}`).then(success).catch(fail);
}
function statusLikes(param, success, fail) {
  local.post(`/board/info/like`, param).then(success).catch(fail);
}
function addLikes(param, success, fail) {
  // console.log("addLikes", param);
  local.post(`/board/addlike`, param).then(success).catch(fail);
}
function delLikes(param, success, fail) {
  // console.log("delLikes", param);
  local.post(`/board/dellike`, param).then(success).catch(fail);
}
function getBoardListPg(param, success, fail) {
  local.get(`/board/`)
  //content, pgno, condition
}


export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  writeComment,
  listComment,
  modifyComment,
  deleteComment,
  searchArticle,
  getNickname,
  cntBoardLikes,
  statusLikes,
  addLikes,
  delLikes,
  listArticlePg
};
