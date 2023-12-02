package com.sj.trip.attraction.model.service;

import com.sj.trip.attraction.dto.AttractionLikeDto;

import java.sql.SQLException;
import java.util.List;

public interface AttractionLikeService {
    int getAttrsById(AttractionLikeDto likeInfo) throws SQLException;
    int getNumAttrsByattrId(int attrId) throws SQLException;
    int addAttrLike(AttractionLikeDto likeInfo) throws SQLException;
    int deleteAttrLike(AttractionLikeDto likeInfo) throws SQLException;
    List<AttractionLikeDto> getLikeList(String id)throws SQLException;// 모든 좋아요 눌린 관광지 정보 출력
}
