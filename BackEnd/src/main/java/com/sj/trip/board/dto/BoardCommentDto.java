package com.sj.trip.board.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BoardCommentDto {
    private int boardNo;
    private int commentNo;
    private String id;
    private String content;
    private String createDate;
}
