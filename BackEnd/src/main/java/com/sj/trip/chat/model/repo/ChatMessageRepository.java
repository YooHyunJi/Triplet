package com.sj.trip.chat.model.repo;

import com.sj.trip.chat.dto.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByRoomId(String roomId);
    List<ChatMessage> findAll();
}