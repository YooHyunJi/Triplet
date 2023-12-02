package com.sj.trip.chat.model.service;

import com.sj.trip.chat.dto.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    boolean findRoom(ChatRoom room);
    int makeRoom();
}