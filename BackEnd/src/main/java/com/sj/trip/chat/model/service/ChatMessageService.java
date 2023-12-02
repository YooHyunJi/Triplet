package com.sj.trip.chat.model.service;


import com.sj.trip.chat.dto.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> findByRoomId(String roomId);
    List<ChatMessage> findAll();
    void saveMessage(ChatMessage tmp);
}