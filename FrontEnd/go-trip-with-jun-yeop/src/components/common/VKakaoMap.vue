<script setup>
import { ref, watch, onMounted } from "vue";
import { useAttractionMenuStore } from "@/stores/attractionMenu";

import { storeToRefs } from "pinia";
import { useAttractionInfoStore } from "@/stores/attractionInfo";
import { useMemberStore } from "@/stores/member";
import {
  addLikes,
  cntAttrLikes,
  delLikes,
  statusLikes,
} from "@/api/attraction";
import router from "@/router";
const attractionInfoStore = useAttractionInfoStore();
const { cntLikes, isLike, pathMarkers } = storeToRefs(attractionInfoStore);
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const pathStore = useAttractionInfoStore();

let map;
let selectedMarker;
const attractionMenuStore = useAttractionMenuStore();
const markersInfo = ref([]);
const markers = ref([]);
const props = defineProps({
  routeData: Object,
  findMarkers: Array,
});
const currentMarker = ref({});
const currentMarkerPos = ref({});
const checkFlag = ref(false);
const likeCount = ref(0);
const isUser = "alert('1');alert('2');";
const attrInfo = ref({
  id: "",
  attrId: "",
  attrAddr: "",
  attrImag: "",
  attrLati: "",
  attrLong: "",
  attrName: "",
  attrType: "",
});
const prevPolyLine = ref([]);

const makeInfo = (markerInfo) => {
  const likeToggle = ref(false);

  const newInfo = document.createElement("div");
  newInfo.className = "wrap";

  const info = document.createElement("div");
  info.className = "info";

  const title = document.createElement("div");
  title.className = "title";
  title.innerHTML = markerInfo.title;

  const body = document.createElement("div");
  body.className = "body";

  const divImg = document.createElement("div");
  divImg.className = "img";

  const img = document.createElement("img");
  img.src = markerInfo.image
    ? markerInfo.image
    : "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAA1BMVEXNzs4XqC2+AAAASElEQVR4nO3BgQAAAADDoPlTX+AIVQEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwDcaiAAFXD1ujAAAAAElFTkSuQmCC";

  const desc = document.createElement("div");
  desc.className = "desc";

  const location = document.createElement("p");
  location.className = "location";
  location.innerHTML = markerInfo.address;

  // 좋아요 버튼 테스트 해애 맘껏해
  const clickDiv = async () => {
    // 로그아웃 상태에서 좋아요 누르기 불가
    if(userInfo.value===null){
      alert("여행지 좋아요는 회원만 가능합니다!");
      router.push({"name": "user-login"});
      return;
    }

    isLike.value = !isLike.value;
    let res = await cntAttrLikes(attrInfo.value);
    likeCount.value = res.data;
    if (isLike.value) {
      await addLikes(attrInfo.value);
      likeCount.value++;
    } else {
      await delLikes(attrInfo.value);
      likeCount.value--;
    }
    showInfo();
  }
  const divLike = document.createElement("div");
  divLike.className = "divLike";

  const imgLikeHeart = document.createElement("img");
  imgLikeHeart.className = "imgLikeHeart";
  if(checkFlag.value) imgLikeHeart.src = "./public/heart_full.png";
  else imgLikeHeart.src = "./public/heart_empty.png";
  imgLikeHeart.onclick = clickDiv;

  const divLikeCnt = document.createElement("p");
  divLikeCnt.className = "divLikeCnt";
  divLikeCnt.innerHTML = likeCount.value;

  newInfo.appendChild(info);
  info.appendChild(title);
  info.appendChild(body);
  body.appendChild(divImg);
  divImg.appendChild(img);
  body.appendChild(desc);
  desc.appendChild(location);
  desc.appendChild(divLike);
  divLike.appendChild(divLikeCnt);
  divLike.appendChild(imgLikeHeart);

  return newInfo;
};

const showInfo = async () => {
  // console.log("여그여 여그");
  // console.log(userInfo.value);
  if(userInfo.value!==null) attrInfo.value.id = userInfo.value.id;
  attrInfo.value.attrId = currentMarker.value.id;
  attrInfo.value.attrAddr = currentMarker.value.address;
  attrInfo.value.attrImag = currentMarker.value.image;
  attrInfo.value.attrLati = currentMarker.value.latlng.La;
  attrInfo.value.attrLong = currentMarker.value.latlng.Ma;
  attrInfo.value.attrName = currentMarker.value.title;
  attrInfo.value.attrType = currentMarker.value.attrType;

  let res = await cntAttrLikes(attrInfo.value);
  likeCount.value = res.data;

  if(userInfo.value!==null){
    res = await statusLikes(attrInfo.value);
    if (res.status === 200) {
      checkFlag.value = true;
    } else if (res.status === 204) {
      checkFlag.value = false;
    }
  }
  else{
    checkFlag.value = false;
  }

  if (selectedMarker) selectedMarker.setMap(null);
  const overlay = new kakao.maps.CustomOverlay({
    content: makeInfo(currentMarker.value),
    map: null,
    position: currentMarker.value.latlng,
  });

  removeInfo();
  selectedMarker = overlay;
  overlay.setMap(map);
  map.panTo(overlay.getPosition());
};
const removeInfo = () => {
  if (selectedMarker) selectedMarker.setMap(null);
};
// const makeOverlay = () => {
//   const newOverlay = new kakao.maps.CustomOverlay({
//     content: makeInfo(markerInfo),
//     map: map,
//     position: markerInfo.latlng,
//   });
//   return newOverlay;
// };
watch(
  () => attractionMenuStore.attractions.value,
  () => {
    markersInfo.value = [];
    attractionMenuStore.attractions.forEach((attraction) => {
      let obj = {};
      obj.id = attraction.attrId;
      obj.title = attraction.attrName;
      obj.image = attraction.attrImag;
      obj.address = attraction.attrAddr;
      obj.latlng = new kakao.maps.LatLng(
        attraction.attrLong,
        attraction.attrLati
      );
      obj.attrType = attraction.attrType;

      markersInfo.value.push(obj);
    });
    removeInfo();
    loadMarkers();
  },
  { deep: true }
);

watch(
  () => attractionMenuStore.selectedAttraction.value,
  () => {
    let markerInfo = {};

    markerInfo.id = attractionMenuStore.selectedAttraction.attrId;
    markerInfo.title = attractionMenuStore.selectedAttraction.attrName;
    markerInfo.image = attractionMenuStore.selectedAttraction.attrImag;
    markerInfo.address = attractionMenuStore.selectedAttraction.attrAddr;
    markerInfo.latlng = new kakao.maps.LatLng(
      attractionMenuStore.selectedAttraction.attrLong,
      attractionMenuStore.selectedAttraction.attrLati
    );
    markerInfo.attrType = attractionMenuStore.selectedAttraction.attrType;
    currentMarker.value = markerInfo;

    removeInfo();
    showInfo();
    // currentMarkerPos.value = marker.getPosition();
    // const check = new kakao.maps.CustomOverlay({
    //   content: makeInfo(markerInfo),
    //   map: null,
    //   position: currentMarker.value.latlng,
    // });
    // removeInfo();
    // showInfo();
  },
  { deep: true }
);
watch(
  () => pathStore.pathSection,
  (newRouteData, oldRouteData) => {
    const linePaths = [];
    const distAndTime = [];
    newRouteData.forEach((section) => {
      distAndTime.push({ distance: section.distance, time: section.duration });
      const linePath = [];
      section.roads.forEach((road) => {
        road.vertexes.forEach((vertex, index) => {
          if (index % 2 === 0) {
            linePath.push(
              new kakao.maps.LatLng(
                road.vertexes[index + 1],
                road.vertexes[index]
              )
            );
          }
        });
      });
      linePaths.push(linePath);
    });
    prevPolyLine.value.forEach((line) => {
      line.setMap(null);
    });
    prevPolyLine.value = [];
    //색깔 변수들;
    const colors = ["#FF0000", "#FFA500", "#FFFF00", "#00FF00", "#0000FF"];
    for (let i = 0; i < linePaths.length; i++) {
      const polyline = new kakao.maps.Polyline({
        path: linePaths[i],
        strokeWeight: 5,
        strokeColor: colors[i],
        strokeOpacity: 1.0,
        strokeStyle: "solid",
      });
      prevPolyLine.value.push(polyline);
      polyline.setMap(map);
    }
    deleteMarkers();
    markers.value = [];
    pathMarkers.value.forEach((markerInfo) => {
      const marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(markerInfo.lat, markerInfo.lon),
        clickable: true,
      });
      markers.value.push(marker);
    });

    distAndTime.forEach((data, index) => {
      console.log(
        `${index}부터 ${index + 1}까지 걸리는 거리 : ${data.distance / 1000}KM`
      );
      const hour = Math.floor(data.time / 3600);
      const minute = Math.floor((data.time % 3600) / 60);
      console.log(
        `${index}부터 ${index + 1}까지 걸리는 시간 : ${hour}시간 ${minute}분`
      );
    });
  },
  { deep: true }
);
onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  // map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커를 생성합니다
  markers.value = [];
  markersInfo.value.forEach((markerInfo) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: markerInfo.latlng, // 마커를 표시할 위치
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      id: "data",
    });

    kakao.maps.event.addListener(marker, "mouseover", async () => {
      currentMarker.value = markerInfo;
      currentMarkerPos.value = marker.getPosition();
      showInfo();
    });
    markers.value.push(marker);
  });
  const radioChange = () => {
  };

  // 마커 목록이 한 눈에 보이도록 지도 범위 설정
  const bounds = markersInfo.value.reduce(
    (bounds, markerInfo) => bounds.extend(markerInfo.latlng),
    new kakao.maps.LatLngBounds()
  );
  map.setBounds(bounds);
};

// 현재 지도에 표시되어 있는 마커 목록 전부 삭제
const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  margin: 0 auto;
}

.wrap {
  position: absolute;
  left: 0;
  width: 30vh;
  height: 30vh;
  bottom: 4vh;
  margin-left: -15vh;
  cursor: default;
}

.wrap .info {
  background-color: pink;
  width: 100%;
  height: 29vh;
  border-radius: 1vh;
  border-bottom: 1vh solid #ccc;
  border-right: 1vh solid #ccc;
  overflow: hidden;
  background: #fff;
}

.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0vh 0.5vh 1vh #b1b1b1;
}

.info .title {
  background: rgb(150, 165, 172);
  color: white;
  text-align: center;
  height: 4vh;
  line-height: 4vh;
  font-size: 1.9vh;
  /* font-weight: bold; */
  box-sizing: border-box;
}

.info:after {
  content: "";
  position: absolute;
  margin-left: -0.8vh;
  left: 50%;
  width: 2.2vh;
  height: 0.9vh;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}

.info .body {
  width: 30vh;
  height: 25vh;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 1.5vh;
  box-sizing: border-box;
}

.info .img {
  width: 27vh;
  height: 15vh;
  border-radius: 1vh;
  color: #888;
  overflow: hidden;
}

.info .img img {
  height: 100%;
  width: 100%;
  object-fit: cover;
}

.info .desc {
  width: 100%;
  height: 6vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.info .desc .location {
  text-align: center;
  width: 100%;
  white-space: break-spaces;
  font-size: 1.5vh;
  margin: 0;
  overflow: hidden;
}

.info .divLike{
  width: 5vh;
  margin-left: auto;
  text-align: right;
  display: flex;
  justify-content: flex-end;
}

.info .divLike .divLikeCnt{
  margin: 0;
  font-size: 1.5vh;
  line-height: 2.2vh;
}

.info .divLike .imgLikeHeart{
  width: 2vh;
  height: 2.2vh;
  margin-left: 0.5vh;
  cursor: pointer;
}
</style>
