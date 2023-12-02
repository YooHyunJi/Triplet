import { ref } from "vue";
import { defineStore } from "pinia";
import {
  listAttractions,
  listKeyWordAttractions,
} from "@/api/attraction";

export const useAttractionMenuStore = defineStore("attractionMenuStore", () => {
  const contentTypeList = ref([
    { text: "모든 유형", value: "0", image:"" } ,
    { text: "관광지", value: "12", image:"&#127904;" },
    { text: "문화 시설", value: "14", image:"&#128444;" },
    { text: "축제/공연/행사", value: "15", image:"&#127962;" },
    { text: "여행 코스", value: "25", image:"&#128506;" },
    { text: "레포츠", value: "28", image:"&#127938;" },
    { text: "숙박", value: "32", image:"&#127960;" },
    { text: "쇼핑", value: "38", image:"&#127869;" },
    { text: "음식점", value: "39", image:"&#127860;" },
  ]);

  const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;
  const attractions = ref([]);
  const selectedAttraction = ref({});
  const isAttractions = ref(false);
  const paramKeyWord = ref("");
  const paramAttraction = ref({
    serviceKey: VITE_OPEN_API_SERVICE_KEY,
    numOfRows: 100,
    pageNo: 1,
    MobileOS: "ETC",
    MobileApp: "AppTest",
    _type: "json",
    listYN: "Y",
    arrange: "A",
    areaCode: 1,
  });

  // 시도 변경
  const onChangeSido = (val) => {
    //console.log(val);
    paramAttraction.value.areaCode = val;
    getAttractions();
  };

  // 관광지 유형 변경
  const onChangeType = (val) => {
    if (val === 0) {
      delete paramAttraction.value.contentTypeId;
    } else {
      paramAttraction.value.contentTypeId = val;
    }
    getAttractions();
  };

  // 키워드 변경
  const onChangeKeyword = () =>{
    const val = document.querySelector("#search-keyword").value;
    paramKeyWord.value = val;
  }

  // 관광지 정보 추출
  const getAttractions = async () => {
    if (paramKeyWord.value === "") {
      delete paramAttraction.value.keyword;
      listAttractions(
        paramAttraction.value,
        ({ data }) => {
          let options = [];
          data.response.body.items.item.forEach((attr) => {
            options.push({
              attrId: attr.contentid,
              attrImag: attr.firstimage,
              attrName: attr.title,
              attrAddr: attr.addr1,
              attrLati: attr.mapx,
              attrLong: attr.mapy,
              attrType: attr.contenttypeid,
              attrKeyw: attr.keyword,
              attrBudget: "",
              attrMemo: "",
            });
            attractions.value = options;
          });
          isAttractions.value = true;
        },
        (err) => {
          isAttractions.value = false;
        }
      );
    } else {
      paramAttraction.value.keyword = paramKeyWord.value;
      listKeyWordAttractions(
        paramAttraction.value,
        ({ data }) => {
          let options = [];
          data.response.body.items.item.forEach((attr) => {
            options.push({
              attrId: attr.contentid,
              attrImag: attr.firstimage,
              attrName: attr.title,
              attrAddr: attr.addr1,
              attrLati: attr.mapx,
              attrLong: attr.mapy,
              attrType: attr.contenttypeid,
              attrKeyw: attr.keyword,
              attrBudget: "",
              attrMemo: "",
            });
            attractions.value = options;
          });
          isAttractions.value = true;
        },
        (err) => {
          isAttractions.value = false;
          //console.log(err);
        }
      );
    }
  };

  // 특정 관광지 선택
  const viewAttraction = (attraction) => {
    selectedAttraction.value = attraction;
  }

  
  return{
    contentTypeList,
    attractions,
    selectedAttraction,
    isAttractions,
    onChangeSido,
    onChangeType,
    onChangeKeyword,
    paramKeyWord,
    paramAttraction,
    getAttractions,
    viewAttraction,
  }
});
