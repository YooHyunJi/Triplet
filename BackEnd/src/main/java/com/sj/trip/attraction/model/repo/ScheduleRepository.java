package com.sj.trip.attraction.model.repo;

import com.sj.trip.attraction.dto.ScheduleDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScheduleRepository extends MongoRepository<ScheduleDto, String> {
    List<ScheduleDto> findByPlanUserIdAndPlanTitle(String userId, String title);
    List<ScheduleDto> findByPlanUserId(String userId);
}
