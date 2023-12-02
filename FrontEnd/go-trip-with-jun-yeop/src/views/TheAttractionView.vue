<script setup>
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import AttractionSearch from "@/components/attraction/AttractionSearch.vue";

</script>

<template>
  <div class="Wrapper">
    <div class="DivContent">
      <VKakaoMap
        class="DivMap"
        :routeData="routeData"
        :findMarkers="findMarkers"
      />
      <AttractionSearch />
    </div>
  </div>
</template>

<style scoped>
.DivContent {
  /* background-color: gray; */
  width: 90%;
  height: 75vh;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}
.DivMap {
  flex: 6;
  height: 100%;
  border-radius: 5%;
  margin: 0 5vh 0 0 !important;
}
</style>
