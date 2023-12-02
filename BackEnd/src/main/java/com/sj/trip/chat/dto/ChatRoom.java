package com.sj.trip.chat.dto;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private int roomId;
    private String name;
    private final Set<WebSocketSession> sessions = new HashSet<>();
}
