package com.sj.trip.chat.model.repo;

import com.sj.trip.chat.dto.ChatRoom;
import com.sj.trip.chat.dto.UserChatMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserChatMappingRepository {
    boolean findRoom(ChatRoom room);
    int makeRoom();

}