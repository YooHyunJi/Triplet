package com.sj.trip.controllers;

import com.sj.trip.chat.dto.ChatMessage;
import com.sj.trip.chat.model.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/chatting")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;
    @GetMapping("/list/{room}")
    public ResponseEntity<?> list(@PathVariable String room) throws SQLException {
        List<ChatMessage> list = chatMessageService.findByRoomId(room);
        if (!list.isEmpty()){
            return new ResponseEntity<List<ChatMessage>>(list, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
