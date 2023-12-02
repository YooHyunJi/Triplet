package com.sj.trip.chat.dto;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "chatting")
public class ChatMessage {
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime sentTime;

}
