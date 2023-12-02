import { ref } from "vue";
import { defineStore } from "pinia";

import {
  cntAttrLikes,
  statusLikes,
  addLikes,
  delLikes
} from "@/api/attraction";
import { httpStatusCode } from "@/util/http-status";

export const useAttractionInfoStore = defineStore("attractionInfoStore", () => {

  const cntLikes = ref(0);
  const isLike = ref(false);
  const pathSection = ref([])
  const pathMarkers = ref([])
  const getCntAttrLikes = async(attrLikeinfo) => {
    await cntAttrLikes(
      attrLikeinfo,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          cntLikes.value = response.data;
        }
      },
      (error) => {
        console.error(error);
      }
    )
  }

  const getStatusLikes = async (attrLikeinfo) => {
    await statusLikes(
      attrLikeinfo,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLike.value = true;
        }
        else {
          isLike.value = false;
        }
        // console.log("log : isLike, ", isLike.value);
      },
      (error) => {
        console.log(error);
      }
    )
  }

  const attrAddLikes = async (attrLikeinfo) => {
    await addLikes(
      attrLikeinfo,
      (response) => {

      },
      (error) => {
        console.log(error);
      }
    )
  }

  const attrDelLikes = async (attrLikeinfo) => {
    await delLikes(
      attrLikeinfo,
      (response) => {
        if (response.status === httpStatusCode.OK) {

        }
        else {

        }
      },
      (error) => {
        console.log(error);
      }
    )
  }



  return {
    cntLikes,
    isLike,
    pathSection,
    pathMarkers,
    getCntAttrLikes,
    getStatusLikes,
    attrAddLikes,
    attrDelLikes,
  }
})