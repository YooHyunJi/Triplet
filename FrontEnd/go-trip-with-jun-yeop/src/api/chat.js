import { localAxios } from "@/util/http-commons";
const local = localAxios();
function getChatMessages(roomId) {
    return local.get(`/chatting/list/${roomId}`);
}
export {
    getChatMessages,
  };