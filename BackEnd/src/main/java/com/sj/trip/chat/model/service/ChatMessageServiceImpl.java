package com.sj.trip.chat.model.service;

import com.sj.trip.chat.dto.ChatMessage;
import com.sj.trip.chat.model.repo.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService{
    private final ChatMessageRepository chatMessageRepository;
    @Override
    public List<ChatMessage> findByRoomId(String roomId) {
        return chatMessageRepository.findByRoomId(roomId);
    }

    @Override
    public List<ChatMessage> findAll() {
        return chatMessageRepository.findAll();
    }
    public void saveMessage(ChatMessage tmp) {
        chatMessageRepository.save(tmp);
    }
}
