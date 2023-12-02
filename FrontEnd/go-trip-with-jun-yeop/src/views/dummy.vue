<template>
  <div>
    <div id="map" style="width: 100%; height: 100vh">
      <button
        @click="refresh()"
        v-show="isVisible"
        class="btn btn-success translate-middle-x position-absolute sticky-top bottom-0 start-50 mb-2"
        id="refresh"
        style="height: 40px; margin-top: auto"
      >
        현 지도에서 검색
      </button>
      <div class="container-fluid d-flex justify-content-center">
        <div
          class="bg-light sticky-top d-inline-flex flex-row p-2"
          id="searchBar"
        >
          <div
            class="btn-group"
            role="group"
            aria-label="Basic radio toggle button group"
          >
            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="btnradio1"
              autocomplete="off"
              @change="() => radioChange('관광지')"
              @click="() => handleRadioClick('관광지')"
              v-model="selectedValue"
              :checked="selectedValue === '관광지'"
            />
            <label class="btn btn-outline-success" for="btnradio1"
              >관광지</label
            >

            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="btnradio2"
              autocomplete="off"
              @change="() => radioChange('음식점')"
              @click="() => handleRadioClick('음식점')"
              v-model="selectedValue"
              :checked="selectedValue === '음식점'"
            />
            <label class="btn btn-outline-success" for="btnradio2"
              >음식점</label
            >

            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="btnradio3"
              autocomplete="off"
              @change="() => radioChange('숙박')"
              @click="() => handleRadioClick('숙박')"
              v-model="selectedValue"
              :checked="selectedValue === '숙박'"
            />
            <label class="btn btn-outline-success" for="btnradio3">숙박</label>

            <input
              type="radio"
              class="btn-check"
              name="btnradio"
              id="btnradio4"
              autocomplete="off"
              @change="() => radioChange('쇼핑')"
              @click="() => handleRadioClick('쇼핑')"
              v-model="selectedValue"
              :checked="selectedValue === '쇼핑'"
            />
            <label class="btn btn-outline-success" for="btnradio4">쇼핑</label>
          </div>
          <form class="d-flex ms-2">
            <input
              class="form-control me-2"
              type="search"
              placeholder="Search"
              aria-label="Search"
            />
            <button class="btn btn-outline-success" type="submit">
              Search
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, defineEmits } from "vue";

import { listAttraction } from "@/api/map.js";
import { useAttractionStore } from "@/stores/attraction.js";

const { VITE_KAKAO_MAP_API_APP_KEY } = import.meta.env;
const emit = defineEmits(["markerClick"]);
const attractionStore = useAttractionStore();
const { setAttractionList } = attractionStore;
const isVisible = ref(false);

var map;
var markers = ref([]);
var marker;

// 좌표
var bound_lat_long = this;
var swLatLng; // 남서쪽 좌표
var neLatLng; // 북동쪽 좌표

const initMap = async () => {
  const mapContainer = document.getElementById("map");
  const { lat, long } = await currentLocation();

  if (!window.kakao || !window.kakao.maps) {
    await loadKakaoMapScript();
  }

  var mapOption = {
    center: new kakao.maps.LatLng(lat, long),
    level: 3,
  };

  map = new kakao.maps.Map(mapContainer, mapOption);

  bound_lat_long = map.getBounds(); // 현재 보는 화면의 범위 구하기
  swLatLng = bound_lat_long.getSouthWest(); // 남서쪽 좌표
  neLatLng = bound_lat_long.getNorthEast(); // 북동쪽 좌표

  // 처음 로딩 될 때, 현재 위치에서 관광지 리스트 가져오기
  param.value = {
    sw_lat: swLatLng.getLat(),
    sw_lng: swLatLng.getLng(),
    ne_lat: neLatLng.getLat(),
    ne_lng: neLatLng.getLng(),
  };
  getAttractionList();

  // 컨트롤 생성
  var mapTypeControl = new kakao.maps.MapTypeControl();
  map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
  var zoomControl = new kakao.maps.ZoomControl();
  map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

  // 드래그 완료 이벤트
  kakao.maps.event.addListener(map, "dragend", handleMapChange);
  // 확대/축소 이벤트
  kakao.maps.event.addListener(map, "zoom_changed", handleMapChange);
};

const loadKakaoMapScript = async () => {
  return new Promise((resolve) => {
    const script = document.createElement("script");
    script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_KAKAO_MAP_API_APP_KEY}`;
    script.onload = () => {
      kakao.maps.load(() => {
        resolve();
      });
    };
    document.head.appendChild(script);
  });
};

onMounted(() => {
  initMap();
});

// 맵 변경 시 버튼 나타나도록 함
const handleMapChange = () => {
  bound_lat_long = map.getBounds();
  OnButtonVisibility();
  console.log(
    "변경된 위치 : " + bound_lat_long + ":" + swLatLng + ", " + neLatLng
  );
};

// 현위치에서 검색 버튼
function OnButtonVisibility() {
  isVisible.value = true;
}
function OffButtonVisibility() {
  isVisible.value = false;
}

// 화면 새로 고침
var param = ref({});

const refresh = () => {
  OffButtonVisibility();
  bound_lat_long = map.getBounds();
  swLatLng = bound_lat_long.getSouthWest(); // 남서쪽 좌표
  neLatLng = bound_lat_long.getNorthEast(); // 북동쪽 좌표

  param = ref({
    sw_lat: swLatLng.getLat(),
    sw_lng: swLatLng.getLng(),

    ne_lat: neLatLng.getLat(),
    ne_lng: neLatLng.getLng(),
  });

  getAttractionList();
};
let selectedValue = ref("");

const radioChange = (value) => {
  selectedValue.value = selectedValue.value === value ? "" : value;
};

const handleRadioClick = (value) => {
  if (selectedValue.value === value) {
    selectedValue.value = "";
  }
};
// watch(
//   () => attractionStore.attractionList.value,
//   (newVal) => {
//     attractionList.value = newVal;
//     console.log(attractionList.value);
//   },
//   { deep: true }
// );

// onMounted(()=> {
//   swLatLng = bound_lat_long.getSouthWest(); // 남서쪽 좌표
//   neLatLng = bound_lat_long.getNorthEast();
//   console.log("sssss : "+swLatLng)
//   getAttractionList();
// })

// onMounted(() => {
//   // param = ref({
//   // bounds: {
//   //   sw: {
//   //     lat : swLatLng.getLat(),
//   //     lng : swLatLng.getLng(),
//   //   },
//   //   ne: {
//   //     lat : neLatLng.getLat(),
//   //     lng : neLatLng.getLng(),
//   //   },
//   // },
//   // bounds: bound_lat_long,
//   });

//   getAttractionList();
// });

// 관광지 서버에서 가져오기
const getAttractionList = () => {
  console.log("서버에서 관광지 얻어오기!", param.value);
  listAttraction(
    param.value,
    ({ data }) => {
      // markers.value = data.attractions;
      setAttractionList(data);
      loadMarkers();
    },
    (error) => {
      console.log("getAttractionList() Error: " + error);
    }
  );
};

const loadMarkers = () => {
  deleteMarkers(); // 현재 표시되어 있는 marker들 제거

  // 마커 생성
  markers.value = [];
  attractionStore.attractionList.forEach((attraction) => {
    var markerPosition = new kakao.maps.LatLng(
      attraction.latitude,
      attraction.longitude
    );
    // 마커 이미지 생성
    const imagePath = "/marker1.png";
    const imgSize = new kakao.maps.Size(40, 45);
    const markerImage = new kakao.maps.MarkerImage(imagePath, imgSize);

    marker = new kakao.maps.Marker({
      map: map,
      position: markerPosition,
      title: attraction.attractionName,
      clickable: true,
      image: markerImage,
      // attractionNo: attraction.attractionNo,
      id: "data",
    });
    marker.customData = {
      attractionNo: attraction.attractionNo,
      name: attraction.attractionName,
      rating: attraction.rating,
      reviewCount: attraction.reviewCount,
      overview: attraction.overview,
    };

    // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
    var iwContent = `
        <div id="infowindow">
          ${marker.customData.name} <br> 평점: ${marker.customData.rating}
        </div>
      `;

    var infowindow = new kakao.maps.InfoWindow({
      content: iwContent,
      maxWidth: 100, // 최대 가로 크기
      maxHeight: 100,
    });

    // 마커에 클릭 이벤트를 등록합니다
    kakao.maps.event.addListener(
      marker,
      "click",
      ((marker, attraction) => {
        let isOpened = false;

        return function () {
          const markerPosition = marker.getPosition();
          if (isOpened) {
            infowindow.close();
          } else {
            infowindow.setPosition(markerPosition);
            infowindow.open(map, marker);
          }

          isOpened = !isOpened;

          emit("markerClick", marker.customData.attractionNo);

          console.log("클릭한 관광지: " + marker.customData.name);
        };
      })(marker, attraction)
    );

    markers.value.push(marker);
  });
};

const deleteMarkers = () => {
  if (markers.value && markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};

// 현재위치 가져오기
function currentLocation(callback) {
  return new Promise((resolve, reject) => {
    if (navigator.geolocation) {
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(function (position) {
        var lat = position.coords.latitude; // 위도
        var long = position.coords.longitude; // 경도
        resolve({ lat, long });
      });
    } else {
      // HTML5의 GeoLocation을 사용할 수 없을때 임의 위치로 저장.
      var lat = 37.5450159,
        long = 127.1368066;
      resolve({ lat, long });
    }
  });
}

const selectedRadio = ref(null);

// 필터 버튼 토글
const handleFilterClick = (event) => {
  const data = event.target.value;
  console.log("변경 전 data: " + data + " value : " + selectedRadio.value);
  if (selectedRadio.value === null) {
    selectedRadio.value = data;
  } else if (selectedRadio.value === data) {
    console.log("같음");
    selectedRadio.value = null;
  }
  console.log("변경 후 value: " + selectedRadio.value);
};

// watch(
//   () => selectedRadio,
//   (newVal) => {
//     console.log(selectedRadio);
//   },
//   { deep: true }
// );
</script>

<style>
#infowindow {
  display: block;
  background: #198754;
  color: #fff;
  text-align: center;
  width: 170px;
  justify-content: center;
  /* height: 24px;
    line-height: 22px; */
  border-radius: 4px;
  padding: 0px 10px;
}
</style>
