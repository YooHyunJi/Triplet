<script setup>
import { ref, onMounted } from "vue";
import { getNickname} from "@/api/board";

const props = defineProps({
  article: Object
});

const nick = ref("");

onMounted(async () => {
  const res = await getNickname(props.article.id);
  nick.value = res.data.nickName;

})
</script>

<template>
  <tr class="text-center">
    <td scope="row">{{ article.boardNo }}</td>
    <td class="text-start">
      <router-link
        :to="{
          name: 'article-view',
          params: { articleno: article.boardNo },
          query: { nick: nick },
        }"
        class="article-title link-dark"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <td>{{ nick }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.createDate }}</td>
  </tr>
</template>

<style scoped>
tr{
  background-color: rgb(230, 230, 230);
  height: 5vh;
}

tr:hover{
  background-color: rgba(122, 133, 139, 0.5);
  color: rgb(230, 230, 230);
  transition: 0.4s;
}

tr a:hover{
  color: rgb(230, 230, 230);
  transition: 0.4s;
}
</style>
