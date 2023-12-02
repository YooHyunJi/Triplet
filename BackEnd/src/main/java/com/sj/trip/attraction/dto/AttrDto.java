package com.sj.trip.attraction.dto;

import lombok.Data;

@Data
public class AttrDto {
    private String attrId;
    private String attrImag;
    private String attrName;
    private String attrAddr;
    private String attrLati;
    private String attrLong;
    private String attrType;
    private int attrBudget;
    private String attrMemo;
}