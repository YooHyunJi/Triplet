package com.sj.trip.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@AllArgsConstructor
@Slf4j
public class SearchCondition {

    private int condition;
    private String content;
    private String orderBy = "none";
    private String orderByDir;

    // 1. 제목 : subject
    // 2. 작성자 : userId
    // 3. 내용 : content
    public SearchCondition(int condition, String content){
        this(condition, content, "none");
//        log.debug(condition + " " + content);
    }

    public SearchCondition(int condition, String content, String orderBy){ this(condition, content, orderBy, "asc"); }

    public SearchCondition(int condition, String content, String orderBy, String orderByDir){
        this.condition = condition;
        this.content = content;
        this.orderBy = orderBy;
        this.orderByDir = orderByDir;
    }

}