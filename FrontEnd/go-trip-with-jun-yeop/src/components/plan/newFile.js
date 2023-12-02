import { onMounted } from "vue";
import { isReadonly, route } from "./PlanAttraction.vue";

onMounted(() => {
// console.log(route);
// console.log(route.params);
if (isReadonly(route.params)) {
const btns = document.querySelectorAll(".BtnAttractionRemove");
console.log(btns);

// btn.style.display = "none";
}
});
