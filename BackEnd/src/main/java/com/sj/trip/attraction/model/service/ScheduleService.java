package com.sj.trip.attraction.model.service;

import com.sj.trip.attraction.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDto> findByPlanUserIdAndPlanTitle(String userId, String title);
    List<ScheduleDto> findByPlanUserId(String userId);

    public void saveSchedule(ScheduleDto scheduleDto);
}
