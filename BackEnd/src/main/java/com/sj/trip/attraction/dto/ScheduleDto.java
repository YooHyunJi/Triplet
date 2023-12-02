package com.sj.trip.attraction.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@Document(collection = "schedules")
public class ScheduleDto {
    @Id
    private String id;
    private String planUserId;
    private String planTitle;
    private String planPeople;
    private List<List<AttrDto>> planList;
}