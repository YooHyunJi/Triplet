package com.sj.trip.controllers;

import com.sj.trip.chat.dto.ChatMessage;
import com.sj.trip.chat.model.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "true")
public class StompChatController {

    //특정 Broker 에게 메시지를 전달
    private final SimpMessagingTemplate template;
    private final ChatMessageService chatMessageService;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessage message){
        message.setMessage(message.getSender() + "님이 채팅방에 입장하셨습니다");
        System.out.println(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        // sub/sub/shat/room
    }

    @MessageMapping(value = "/chat/message/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, ChatMessage message){
        log.debug("[StompChatController - sendMessage]: message = {}, to room id = {}", message, roomId );
//        chatMessageService.saveChatMessage(message);
        chatMessageService.saveMessage(message);
        template.convertAndSend("/sub/" + roomId , message);
    }
}
