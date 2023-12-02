package com.sj.trip.attraction.dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Component
public class AttractionLikeDto {
    private String id;
    private int attrId;
    private String attrAddr;
    private String attrImag;
    private double attrLati;
    private double attrLong;
    private String attrName;
    private int attrType;

}