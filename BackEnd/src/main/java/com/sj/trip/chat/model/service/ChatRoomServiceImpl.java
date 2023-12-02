package com.sj.trip.chat.model.service;

import com.sj.trip.chat.dto.ChatRoom;
import com.sj.trip.chat.model.repo.UserChatMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{
    private final UserChatMappingRepository userChatMappingRepository;
    @Override
    public boolean findRoom(ChatRoom room) {
        boolean flag = userChatMappingRepository.findRoom(room);

        return false;
    }

    @Override
    public int makeRoom() {
        return 0;
    }
}
