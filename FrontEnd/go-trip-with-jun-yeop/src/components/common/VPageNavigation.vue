<script setup>
import { computed } from "vue";

const props = defineProps({ currentPage: Number, totalPage: Number });
const emit = defineEmits(["pageChange"]);

const navigationSize = parseInt(import.meta.env.VITE_ARTICLE_NAVIGATION_SIZE);

const startPage = computed(() => {
  return parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1;
});

const endPage = computed(() => {
  let lastPage =
    parseInt((props.currentPage - 1) / navigationSize) * navigationSize + navigationSize;
  return props.totalPage < lastPage ? props.totalPage : lastPage;
});

const endRange = computed(() => {
  return parseInt((props.totalPage - 1) / navigationSize) * navigationSize < props.currentPage;
});

function range(start, end) {
  const list = [];
  for (let i = start; i <= end; i++) list.push(i);
  return list;
  //   return Array(end - start + 1)
  //     .fill()
  //     .map((val, i) => start + i);
}

function onPageChange(pg) {
  emit("pageChange", pg);
}
</script>

<template>
<div class="Row">
  <div class="RowContent">
    <a class="page-link" @click="onPageChange(1)">최신</a>
    <a class="page-link" @click="onPageChange(startPage == 1 ? 1 : startPage - 1)">이전</a>
    <template v-for="pg in range(startPage, endPage)" :key="pg">
      <a class="page-link" @click="onPageChange(pg)">{{ pg }}</a>
    </template>
    <a class="page-link" @click="onPageChange(endRange ? totalPage : endPage + 1)">다음</a>
    <a class="page-link" @click="onPageChange(totalPage)">마지막</a>
  </div>
</div>
</template>

<style scoped>
.Row{
  /* background-color: orange; */
}

.RowContent{
  /* background-color: pink; */
  width: 60vh;
  margin: 5vh auto 0 auto;
  display: flex;
  justify-content: space-evenly;
}

a {
  padding: 1vh;
  cursor: pointer;
  /* background-color: skyblue; */
}
</style>
