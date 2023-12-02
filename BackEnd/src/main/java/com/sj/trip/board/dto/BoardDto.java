package com.sj.trip.board.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BoardDto {
    private int boardNo;
    private String id;
    private String subject;
    private String content;
    private byte[] img;
    private int hit;
    private String createDate;
}
