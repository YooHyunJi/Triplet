package com.sj.trip.attraction.model.repo;

import com.sj.trip.attraction.dto.AttractionLikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface AttractionLikeRepo {
    // 회원의 해당 관광지 좋아요 추가 여부
    AttractionLikeDto getAttrsById(String id, int attrId) throws SQLException;
    // 해당 관광지의 attrId - cnt ( 좋아요 개수 불러오기 )
    Integer getNumAttrsByattrId(int attrId) throws SQLException;
    // 좋아요 추가
    int addAttrLike(AttractionLikeDto likeInfo) throws SQLException;
    // 좋아요 삭제
    int deleteAttrLike(String id, int attrId) throws SQLException;
    List<AttractionLikeDto> getLikeList(String id)throws SQLException; // 모든 좋아요 눌린 관광지 정보 출력



}
