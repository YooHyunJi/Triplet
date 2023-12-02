import {
  localAxios,
  sidoAxios,
  attractionAxios,
  attractionKeyWordAxios,
  multiPathAxios,
} from "@/util/http-commons";


const local = localAxios();
const sido = sidoAxios();
const attraction = attractionAxios();
const keyWordAttraction = attractionKeyWordAxios();
const findRoute = multiPathAxios();

async function listSido(param, success, fail) {
  // com.ssafy.vue.map.controller에서 sido로 매핑된 기능 수행
  await sido.get("", { params: param }).then(success).catch(fail);
}
function listAttractions(param, success, fail) {
  attraction.get("", { params: param }).then(success).catch(fail);
}
function listKeyWordAttractions(param, success, fail) {
  keyWordAttraction.get("", { params: param }).then(success).catch(fail);
}
function findMultiRoute(routes, success, fail) {
  if (routes.length < 2) return "2개 이상의 목적지를 설정해주세요!";
  const param = {
    origin: routes[0].lon + "," + routes[0].lat,
    destination:
      routes[routes.length - 1].lon + "," + routes[routes.length - 1].lat,
  };
  const wayPoint = routes.slice(1, routes.length - 1);
  let query = "";
  for (let i = 0; i < wayPoint.length; i++) {
    query += wayPoint[i].lon + "," + wayPoint[i].lat + "|";
  }
  param.waypoints = query.slice(0, -1);
  findRoute.get("", { params: param }).then(success).catch(fail);
}

async function cntAttrLikes(param) {
  return await local.get(`/attr/info/cnt/${param.attrId}`);
}
async function statusLikes(param) {
  return await local.post(`/attr/info/like`, param);
}
async function addLikes(param, success, fail) {
  return await local.post(`/attr/addlike`, param).then(success).catch(fail);
}
async function delLikes(param, success, fail) {
  return await local.post(`/attr/dellike`, param).then(success).catch(fail);
}
async function savePlanToDB(param) {
  return await local.post(`attr/save/plan`, param);
}
async function getPlansFromDB(planUserId, planTitle) {
  return await local.get(`attr/find/plan/${planUserId}/${planTitle}`);
}
async function getPlansById(planUserId) {
  return await local.get(`attr/find/plan/${planUserId}`);
}
export {
  listAttractions, listSido, listKeyWordAttractions,
  findMultiRoute, cntAttrLikes, statusLikes,
  addLikes, delLikes,savePlanToDB, getPlansFromDB, getPlansById
};
