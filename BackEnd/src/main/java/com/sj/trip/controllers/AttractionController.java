package com.sj.trip.controllers;


import com.sj.trip.attraction.dto.AttractionLikeDto;
import com.sj.trip.attraction.dto.ScheduleDto;
import com.sj.trip.attraction.model.service.AttractionLikeService;
import com.sj.trip.attraction.model.service.ScheduleService;
import com.sj.trip.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/attr")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AttractionController {
    private final AttractionLikeService attractionLikeService;
    private final ScheduleService scheduleService;

    // 회원의 해당 관광지 좋아요 추가 여부
    @PostMapping("/info/like")
    public ResponseEntity<Integer> isLike(@RequestBody AttractionLikeDto attractionLikeDto) {
        log.debug("isLike : {}", attractionLikeDto);
        int result = 0;
        HttpStatus status = HttpStatus.OK;
        try {
            result = attractionLikeService.getAttrsById(attractionLikeDto);
            if (result == 0)
                status = HttpStatus.NO_CONTENT;

        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(result, status);
    }

    // 해당 관광지의 attrId - cnt ( 좋아요 개수 불러오기 )
    @GetMapping("/info/cnt/{attrId}")
    public ResponseEntity<?> getCntAttrLike(@PathVariable("attrId") int attrId){
        log.debug("getCntAttrLike : {}", attrId);
        int result = 0;
        HttpStatus status = HttpStatus.OK;
        try {
            result = attractionLikeService.getNumAttrsByattrId(attrId);

        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(result, status);
    }

    // 좋아요 추가
    @PostMapping("/addlike")
    public ResponseEntity<?> addLike(@RequestBody AttractionLikeDto attractionLikeDto) {
        log.debug("addLike : {}", attractionLikeDto);
        int result = 0;
        HttpStatus status = HttpStatus.OK;
        try {
            result = attractionLikeService.addAttrLike(attractionLikeDto);
            if (result == 0)
                status = HttpStatus.NO_CONTENT;

        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(result, status);
    }

    // 좋아요 삭제
    @PostMapping("/dellike")
    public ResponseEntity<?> delLike(@RequestBody AttractionLikeDto attractionLikeDto) {
        log.debug("dellike : {}", attractionLikeDto);
        int result = 0;
        HttpStatus status = HttpStatus.OK;
        try {
            result = attractionLikeService.deleteAttrLike(attractionLikeDto);
            if (result == 0)
                status = HttpStatus.NO_CONTENT;

        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(result, status);
    }

    @GetMapping("/likelist/{id}")
    public ResponseEntity<?> getLikeList(@PathVariable("id") String id){
        log.debug("getLikeList : {}", id);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            List<AttractionLikeDto> likelist = attractionLikeService.getLikeList(id);
            resultMap.put("like-list", likelist);
            if (likelist == null){
                status = HttpStatus.NO_CONTENT;
            }
        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity< Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/save/plan")
    public ResponseEntity<?> saveSchduleList(@RequestBody ScheduleDto scheduleDto){
        scheduleService.saveSchedule(scheduleDto);
        return new ResponseEntity<Integer>(1,HttpStatus.OK);
    }
    @GetMapping("/find/plan/{planUserId}/{planTitle}")
    public ResponseEntity<?> findPlan(@PathVariable("planUserId") String planUserId,
                                      @PathVariable("planTitle") String planTitle){
        List<ScheduleDto> list = scheduleService.findByPlanUserIdAndPlanTitle(planUserId,planTitle);
        if (!list.isEmpty()){
            return new ResponseEntity<List<ScheduleDto>>(list, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/find/plan/{planUserId}")
    public ResponseEntity<?> findPlan(@PathVariable("planUserId") String planUserId){
        List<ScheduleDto> list = scheduleService.findByPlanUserId(planUserId);
        if (!list.isEmpty()){
            return new ResponseEntity<List<ScheduleDto>>(list, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
