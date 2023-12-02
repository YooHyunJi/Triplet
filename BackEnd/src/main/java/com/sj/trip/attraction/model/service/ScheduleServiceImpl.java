package com.sj.trip.attraction.model.service;

import com.sj.trip.attraction.dto.ScheduleDto;
import com.sj.trip.attraction.model.repo.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public void saveSchedule(ScheduleDto scheduleDto){
        scheduleRepository.save(scheduleDto);
    }
    @Override
    public List<ScheduleDto> findByPlanUserIdAndPlanTitle(String userId, String title) {
        return scheduleRepository.findByPlanUserIdAndPlanTitle(userId,title);
    }

    @Override
    public List<ScheduleDto> findByPlanUserId(String userId) {
        return scheduleRepository.findByPlanUserId(userId);
    }
}
