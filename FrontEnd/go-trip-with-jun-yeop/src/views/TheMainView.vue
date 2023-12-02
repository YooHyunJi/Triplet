<script setup>
import { ref } from 'vue';

import ChattingCard from "../components/chat/ChattingCard.vue";

const locations = 
[
	{
		"name": "서울",
		"desc": "대한민국의 심장, 서울!",
		"img": "https://img.freepik.com/premium-photo/cherry-blossom-of-spring-in-seoul-south-korea-soft-focus-background_40171-62.jpg",
	},
	{
		"name": "부산",
		"desc": "대도시와 바다의 조화",
		"img": "https://i.namu.wiki/i/3AhrQ2y3Fq2T-px45RpfhOthBtbpmP1kLRtGZOytypMORfHRoFXkj-pl2TlVk5O5Ho4sFDfwFSAM1mBECIUr9A.webp",
	},
	{
		"name": "제주",
		"desc": "아름다운 국내 대표 휴양지",
		"img": "https://a.cdn-hotels.com/gdcs/production103/d928/f0685355-ca99-4f7a-ad98-f8c9ddc3bd1b.jpg",
	},
	{
		"name": "인천",
		"desc": "갈매기가 춤추는 바다의 도시",
		"img": "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/6hI0/image/35IK9knTSksq0mCXulL3unEaKls",
	},
	{
		"name": "여수",
		"desc": "여수 밤바다, 나와 너",
		"img": "https://m.cruisestory.co.kr/data/goods/1/2021/04/216_temp_16188136584946view.jpg",
	},
	{
		"name": "속초",
		"desc": "황홀한 일몰과 함께하는 여행",
		"img": "https://cdn.polinews.co.kr/news/photo/201702/303575_1.jpg",
	},
	{
		"name": "경주",
		"desc": "역사와 함께하는 전통의 도시",
		"img": "https://www.gyeongju.go.kr/upload/content/thumb/20200317/A9E0A9F412B84EC68D1893C36D01E942.jpg",
	},
];
</script>

<template>
<div class="Wrapper">
	<div class="TitleChatting">
		<div class="TitleWrapper">
			<p class="pTop">어디로 떠나볼까요?</p>
			<p class="pBottom">카드를 누르면 해당 지역 오픈 채팅방으로 이동해요!</p>
			<p class="pBottom">여러 사용자들과 함께 실시간으로 정보를 공유해보세요</p>
		</div>
	</div>
	<div class="ChattingWrapper">
		<ChattingCard
			v-for="loc in locations"
			:key = loc.name
			:loc = loc
		/>
	</div>
</div>
</template>

<style scoped>

.ChattingWrapper{
  /* background: linear-gradient(rgb(15, 53, 26), rgb(11, 41, 20)); */
  padding: 0 10vh;
  box-sizing: border-box;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  flex-wrap: wrap;
}

.TitleChatting{
  background-color: rgb(220, 220, 220);
	box-shadow: 0.5vh 0.7vh 2vh 0.1vh rgba(0, 0, 0, 0.05);
  color: rgb(78, 74, 67);
  padding: 10vh 0;
  box-sizing: border-box;
  margin: 15vh auto;
  width: 80%;
  text-align: center;
  border-radius: 1vh;
	transition: 0.5s;
}

.TitleChatting:hover{
	box-shadow: 0.5vh 0.7vh 1vh 0.7vh rgba(0, 0, 0, 0.1);
	transition: 0.5s;
}

.pTop{
	/* background-color: green; */
	font-size: 2.7vh;
	font-style: italic;
	margin-bottom: 3vh;
	color: rgb(196, 48, 48);
	font-weight: 600;
}

.pBottom{
	font-size: 1.5vh;
	line-height: 1.5vh;
}

</style>