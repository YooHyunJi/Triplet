<script setup>
import { VueDraggableNext } from "vue-draggable-next";
import { ref } from "vue";

const list1 = ref([
  { name: "Attraction 1", desc: "desription1" },
  { name: "Attraction 2", desc: "desription2" },
  { name: "Attraction 3", desc: "desription3" },
  { name: "Attraction 4", desc: "desription4" },
]);

const list2 = ref([
  { name: "Attraction 5", desc: "desription5" },
  { name: "Attraction 6", desc: "desription6" },
  { name: "Attraction 7", desc: "desription7" },
]);

const day1 = ref([
]);

const day2 = ref([
]);

const checkMove = (event) => {
  console.log("checkMove", event.draggedContext);
  console.log("Future index: " + event.draggedContext.futureIndex);
};
const log = (event) => {
  const { moved, added } = event;
};
</script>

<template>
  <div class="out">
    <div class="fromList">
      <div class="in from1">
        <h3>Search</h3>
        <VueDraggableNext
          class="dragArea list-group"
          :list="list1"
          :group="{ name: 'people', pull: 'clone', put: false }"
          @change="log"
          :move="checkMove"
        >
          <div
            class="list-group-item"
            v-for="element in list1"
            :key="element.name"
          >
            {{ element.name }}
          </div>
        </VueDraggableNext>
      </div>
      <div class="in from2">
        <h3>Like</h3>
        <VueDraggableNext
          class="dragArea list-group"
          :list="list2"
          :group="{ name: 'people', pull: 'clone', put: false }"
          :move="checkMove"
          item-key="name"
        >
        <div
          class="list-group-item"
          v-for="element in list2"
          :key="element.name">
          {{ element.name }}
        </div>
        </VueDraggableNext>
      </div>
    </div>
<!-- -------------------------------------------------------------------- -->
    <div class="toList">
      <h3>Plan</h3>
      <VueDraggableNext
          class="dragArea day"
          :list="day1"
          group="people"
          :move="checkMove"
          item-key="name"
        >
        <div
          class="list-group-item"
          v-for="element in day1"
          :key="element.name">
          <div> {{ element.name }} </div>
          <div> {{ element.desc }} </div>
          <div> <input type="text"> </div>
          <div> <textarea name="" desc="" cols="30" rows="2"> </textarea></div>
        </div>
        </VueDraggableNext>

        <VueDraggableNext
          class="dragArea"
          :list="day2"
          group="people"
          :move="checkMove"
          item-key="name"
        >
        <div
          class="list-group-item"
          v-for="element in day2"
          :key="element.name">
          <div> {{ element.name }} </div>
          <div> {{ element.desc }} </div>
          <div> <input type="text"> </div>
          <div> <textarea name="" desc="" cols="30" rows="2"> </textarea></div>
        </div>
        </VueDraggableNext>
    </div>

  </div>

</template>

<style scoped>
.out{
  width: 600px;
  height: 750px;
  display: flex;
  margin: 20px;
  /* background-color: orange; */
}

.fromList{
  /* background-color: orange; */
  flex: 1;
  display: flex;
  flex-direction: column;
  margin-right: 10px;
}
.from1{
  margin-bottom: 10px;
}

input, textarea{
  border: none;
  padding: 5px;
  box-sizing: border-box;
  width: 100%;
}

input, textarea:focus{
  outline: none;
}

input{
  margin: 10px 0;
}

.toList{
  background-color: rgb(240, 240, 240);
  /* background-color: rgb(182, 182, 182); */
  padding-right: 10px;
  flex: 1;
  height: 750px;
  overflow-y: scroll ;
}

.toList .dragArea{
  background-color: rgb(114, 111, 111);
  border-radius: 10px;
  margin: 5px;
  overflow-y: scroll;
}

h3{
  background-color: rgb(206, 161, 161);
  color: rgb(27, 27, 27);
  height: 50px;
  margin: 0;
  line-height: 50px;
  text-align: center;
  border-radius: 10px 10px 0 0;
}

.toList h3{
  background-color: rgb(122, 143, 136);
  color: rgb(231, 231, 231);
}

.dragArea{
  background-color: rgb(240, 240, 240);
  padding: 10px;
  box-sizing: border-box;
  border-radius: 0 0 10px 10px;
}

.list-group-item{
  background-color: rgb(219, 219, 219);
  padding: 10px;
  margin: 0 0 10px 0;
  border-radius: 5px;
}

.list-group-item:last-child{
  margin: 0
}
</style>
