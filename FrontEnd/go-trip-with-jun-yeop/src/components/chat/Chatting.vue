<template>
  <div class="Wrapper">
    <div class="chat-container">
      <div class="RoomName">{{ props.roomName }}</div>
      <div class="chat-messages">
        <div
          v-for="message in recvList"
          :key="message.id"
          class="message"
          :class="{
            left: isLeftMessage(message),
            right: !isLeftMessage(message),
          }"
        >
          {{ getMessageText(message) }}
        </div>
      </div>
      <div class="chat-input">
        <textarea
          v-model="newMessage"
          @keyup.enter="sendMessage"
          placeholder="메세지를 입력하세요"
        >
        </textarea>
        <button type="button" @click="sendMessage">⏏︎</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client/dist/sockjs";
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member"
import {getChatMessages} from "@/api/chat";
import { storeToRefs } from "pinia";
import { useRoute } from 'vue-router'
const route = useRoute()

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const recvList = ref([]);
const stompClient = ref(null); // Initialize stompClient
const newMessage = ref("");
const user = ref("");
const props = defineProps({
  roomName: Object,
});


onMounted(async () => {
  const prevChat = await getChatMessages(props.roomName);
  if (prevChat.data !== "") {
    prevChat.data.forEach((message) => { 
      recvList.value.push(message);
    })
  }
  connect();
});

const sendMessage = () => {
  send();
  newMessage.value = "";
};

const send = () => {
  if (stompClient.value && stompClient.value.connected) {
    const currentDate = new Date();
    const currentDateString = currentDate.toISOString();
    let msg = {
      sender : userInfo.value.nickName,
      message: newMessage.value,
      roomId: props.roomName,
      sentTime : currentDateString,
    };
    stompClient.value.send(
      "/pub/chat/message/" + msg.roomId,
      JSON.stringify(msg),
      {}
    );
  } else {
    alert("서버 오류로 인해 메시지 전송이 실패하였습니다!")
  }
};

const connect = () => {
  const serverURL = `http://localhost:8080/vue/chat`;
  let socket = new SockJS(serverURL);
  stompClient.value = Stomp.over(socket);
  stompClient.value.connect(
    {withCredentials: true},
    (frame) => {
      // 소켓 연결 성공

      stompClient.value.subscribe(`/sub/${props.roomName}`, (res) => {
        // stompClient.value.subscribe("/sub?myUserId=" + loginUserState.value.userId + "&otherUserId=" + otherUserId.value, res => {

        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        recvList.value.push(JSON.parse(res.body));
      });
    },
    (error) => {
      // 소켓 연결 실패
      console.error(error);
    }
  );
};

const isLeftMessage = (message) => {
  return message.sender !== userInfo.value.nickName;
};

const getMessageText = (message) => {
  return isLeftMessage(message)
    ? `${message.sender}: ${message.message}`
    : `${message.message}`;
};
</script>

<style>
.chat-container {
  width: 60%;
  min-width: 40vh;
  margin: 0 auto;
  box-sizing: border-box;
  border-radius: 1vh;
  padding-right: 1vh;
}

.RoomName{
  background: linear-gradient(rgb(122, 137, 145), rgb(115, 129, 136));
  color: rgb(240, 240, 240);
  line-height: 6vh;
  border-radius: 1vh 1vh 0 0;
  text-align: center;
  font-size: 2vh;
  font-style: italic;
}

.chat-messages {
  background-color: rgb(245, 245, 245);
  padding: 3vh;
  box-sizing: border-box;
  border-radius: 0 0 1vh 1vh;
  height: 55vh;
  overflow-y: scroll;
  margin-bottom: 5vh;
  display: flex;
  flex-direction: column;
}

.message {
  background-color: rgb(230, 230, 230);
  box-shadow: 0.2vh 0.2vh 0.2vh 0.1vh rgba(0, 0, 0, 0.1);
  max-width: 80%;
  width:fit-content;
  padding: 2vh;
  box-sizing: border-box;
  margin-bottom: 1.3vh;
  border-radius: 1vh;
  color: rgb(116, 116, 116);
}

.chat-input {
  display: flex;
  justify-content: space-between;
}

.chat-input textarea {
  background-color: rgb(250, 250, 250);
  /* flex: 1; */
  height: 10vh;
  padding: 2vh;
  margin-right: 1vh;
  border-radius: 1vh;
  resize: none;
}

.chat-input textarea:focus {
  outline: none;
}

.chat-input button {
  background-color: rgba(194, 48, 48, 0.6);
  box-shadow: 0.2vh 0.2vh 0.2vh 0.1vh rgba(0, 0, 0, 0.1);
  color: rgb(240, 240, 240);
  cursor: pointer;
  padding: 0 1.5vh;  
  line-height: 3vh;
  width: auto;
  height: 4vh;
  border-radius: 1vh;
  transition: 0.4s;
  margin-top: auto
}

.chat-input button:hover {
  background-color: rgba(194, 48, 48, 0.8);
  transition: 0.4s;
}

.chat-input button:focus {
  outline: none;
}

.message.right {
  background-color: rgba(122, 137, 145, 0.7);
  color: white;
  text-align: right;
  margin-left: auto;
}
</style>
