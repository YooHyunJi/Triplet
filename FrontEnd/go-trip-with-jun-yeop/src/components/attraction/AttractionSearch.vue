<script setup>
import { ref, onMounted } from "vue";
import { useAttractionMenuStore } from "@/stores/attractionMenu";
import { VueDraggableNext } from "vue-draggable-next";

import VSelect from "../common/VSelect.vue";
import {
  listSido,
  findMultiRoute,
} from "@/api/attraction";

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;
const attractionMenuStore = useAttractionMenuStore();

const sidoList = ref([]);
const typeList = ref([]);
const paramSido = ref({
  serviceKey: VITE_OPEN_API_SERVICE_KEY,
  pageNo: 1,
  MobileOS: "ETC",
  MobileApp: "AppTest",
  _type: "json",
});

// 시도 리스트 받아오기
const getSidoList = async () => {
  await listSido(
    paramSido.value,
    ({ data }) => {
      let options = [];
      options.push({ text: "시/도", value: "" });
      data.response.body.items.item.forEach((sido) => {
        options.push({
          text: sido.name,
          value: sido.code,
        });
      });
      sidoList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

// 관광지 유형 가져오기
const getTypeList = async () => {
  let options = [];
  options.push({ text: "유형", value: "" });
  attractionMenuStore.contentTypeList.forEach((type) => {
    options.push({
      text: type.text,
      value: type.value,
      image: type.image,
    });
  });
  typeList.value = options;
};

//경로찾기
const findFastPath = () => {
  //임시 더미 데이터
  const coordinates = [
    { lat: 37.661708655599995, lon: 127.0665963946 },
    { lat: 37.51721672890002, lon: 126.73401692360001 },
    { lat: 35.2442128801, lon: 129.05579798950134 },
    { lat: 37.8328951114, lon: 128.16019192040002 },
  ];
  findMultiRoute(
    coordinates,
    (response) => {
      const sections = [];
      response.data.routes[0].sections.forEach((section) => {
        sections.push(section)
      })

      routeData.value = sections;
    },
    (error) => {
      // 실패 시 동작
      console.error("실패:", error);
    }
  );
};

onMounted(async () => {
  await getSidoList();
  await getTypeList();
  await attractionMenuStore.getAttractions();
});
</script>

<template>
  <div class="DivSearch">
      <div class="DivForm">
        <form class="FormSearch" @submit.prevent="attractionMenuStore.getAttractions">
          <VSelect
            :selectOption="sidoList"
            @onKeySelect="attractionMenuStore.onChangeSido"
            class="InputSearch"
          />
          <!-- <VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" /> -->
          <VSelect
            :selectOption="typeList"
            @onKeySelect="attractionMenuStore.onChangeType"
            class="InputSearch"
          />
          <input
            id="search-keyword"
            type="text"
            class="InputSearch InputKeyword"
            @change="attractionMenuStore.onChangeKeyword"
            placeholder="검색어"
          />
          <button
            id="btn-search"
            class="BtnSearch"
            type="button"
            @click="attractionMenuStore.getAttractions"
          >
            검색
          </button>
        </form>
      </div>
      <div class="DivResult">
        <div v-show="attractionMenuStore.isAttractions" class="DivResultYes">
          <VueDraggableNext
            class="DragSearch"
            :list="attractionMenuStore.attractions"
            :group="{ name: 'attractions', pull: 'clone', put: false }"
          >
            <div
              v-for="attraction in attractionMenuStore.attractions"
              :key="attraction.attrId"
              @click="attractionMenuStore.viewAttraction(attraction)"
            >
              {{ attraction.attrName }}
            </div>
          </VueDraggableNext>
        </div>
        <div v-show="!attractionMenuStore.isAttractions" class="DivResultNo">
          검색 결과가 없습니다
        </div>
      </div>
    </div>
</template>

<style>
.FormSearch{
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
}

.DivSearch{
  flex: 4;
  height: 100%;
  display: flex;
  box-sizing: border-box;
  margin: 0;
  flex-direction: column;
  justify-content: flex-start;
}

.DivForm{
  width: 100%;
  margin: 0 auto;
  /* height: 30%; */
}

select {
  font-family: 'Pretendard-Regular';
  font-size: 1.5vh;
  -o-appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  cursor: pointer;
  margin-bottom: 1vh;
}

.InputSearch {
  background-color: rgb(218, 222, 224);
  font-family: 'Pretendard-Regular';
  font-size: 1.5vh;
  width: 45%;
  height: 5vh;
  padding: 0 1vh;
  border: none;
  border-radius: 1vh;
}

.InputSearch:focus {
  outline: none;
  /* background-color: rgb(190, 203, 209); */
}

.InputKeyword {
  width: 75%;
}

.BtnSearch {
  background-color: rgb(190, 203, 209);
  font-family: 'Pretendard-Regular';
  font-size: 1.5vh;
  width: 15%;
  padding: 0;
  border-radius: 1vh;
  border: none;
  cursor: pointer;
}
.BtnSearch:hover {
  background-color: rgb(122, 133, 139);
  transition: 0.4s;
}

.DivResult {
  height: 100%;
  overflow-y: scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none;
  display: flex;
  justify-content: space-between;
  padding: 0 2vh 0 0;
  box-sizing: border-box;
  margin: 1vh 0 0 0;
}

.DivResultYes {
  width: 100%;
}

.DivResultNo{
  background-color: rgb(230, 230, 230);
  color: rgb(123, 123, 123);
  border-radius: 2vh;
  text-align: center;
  width: 100%;
  height: 10vh;
  line-height: 10vh;
  margin: 0 auto;
}

.DragSearch{
  text-align: center;
}

.DragSearch div{
  background-color: rgb(230, 230, 230);
  /* height: 5vh; */
  width: 100%;
  padding: 1.5vh;
  box-sizing: border-box;
  border-radius: 1vh;
  cursor: pointer;
  margin-bottom: 1vh;
}

.DragSearch div:hover {
  background-color: rgb(201, 201, 201);
  transition: 0.4s;
}
</style>
