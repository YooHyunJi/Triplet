package com.sj.trip.attraction.model.service;

import com.sj.trip.attraction.dto.AttractionLikeDto;
import com.sj.trip.attraction.model.repo.AttractionLikeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AttractionLikeServiceImpl implements AttractionLikeService {
    private final AttractionLikeRepo attractionLikeRepo;

    @Override
    public int getAttrsById(AttractionLikeDto likeInfo) throws SQLException {
        AttractionLikeDto attractionLikeDto = attractionLikeRepo.getAttrsById(likeInfo.getId(), likeInfo.getAttrId());
        if (attractionLikeDto != null) return 1;
        else return 0;
    }

    @Override
    public int getNumAttrsByattrId(int attrId) throws SQLException{
        Integer result = attractionLikeRepo.getNumAttrsByattrId(attrId);
//        System.out.println(result);
        if (result == null)
            result = 0;
        return result;
    }

    @Override
    public int addAttrLike(AttractionLikeDto likeInfo) throws SQLException{
        return attractionLikeRepo.addAttrLike(likeInfo);
    }

    @Override
    public int deleteAttrLike(AttractionLikeDto likeInfo) throws SQLException{
        return attractionLikeRepo.deleteAttrLike(likeInfo.getId(), likeInfo.getAttrId());
    }

    @Override
    public List<AttractionLikeDto> getLikeList(String id) throws SQLException{
        return attractionLikeRepo.getLikeList(id);
    }
}
