package com.sj.trip.board.dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Component
public class BoardLikeDto {
    private String id;
    private int boardNo;
}
