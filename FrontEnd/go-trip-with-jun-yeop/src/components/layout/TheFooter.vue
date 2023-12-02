<script setup>
</script>

<template>
  <div class="Footer">
    <div class="Footer1">
      <p class="name">Kim Jun Yup</p>
      <p class="position">BackEnd</p>
      <a href="https://github.com/JunYupK" class="github">github.com/JunYupK</a>
    </div>
    <div class="Footer2">
      <p class="name">Kim Han Seul</p>
      <p class="position">BackEnd</p>
      <a href="https://github.com/slcloe" class="github">github.com/slcloe</a>
    </div>
    <div class="Footer3">
      <p class="name">Yoo Hyun Ji</p>
      <p class="position">FrontEnd</p>
      <a href="https://github.com/YooHyunJi" class="github">github.com/YooHyunJi</a>
    </div>
  </div>
</template>

<style scoped>
.Footer{
  background-color: rgb(65, 68, 70);
  box-shadow: 0.5vh 0.7vh 1vh 0.3vh rgba(0, 0, 0, 0.1);
  width: 100%;
  height: 25vh;
  display: flex;
  min-width: 80vh;
}

.Footer div{
  height: 15vh;
  display: flex;
  flex-direction: column;
  margin: auto 5vh 0 5vh;
}

.Footer p, a{
  color: rgb(122, 124, 126);
  font-weight: 600;
}

.Footer .name{
  font-size: 1.7vh;
  line-height: 1vh;
  color: rgb(144, 147, 150);
}

.position{
  font-size: 1.2vh;
}

.github{
  font-size: 1.2vh;
}
</style>