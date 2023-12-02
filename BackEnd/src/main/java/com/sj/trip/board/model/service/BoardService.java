package com.sj.trip.board.model.service;

import com.sj.trip.board.dto.BoardCommentDto;
import com.sj.trip.board.dto.BoardDto;
import com.sj.trip.board.dto.BoardLikeDto;
import com.sj.trip.board.dto.SearchCondition;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BoardService {
    int writeArticle(BoardDto boardDto) throws SQLException;
    List<BoardDto> listArticle() throws SQLException;
    Map<String, Object> listArticlePg(Map<String, String> param) throws SQLException;
    List<BoardDto> searchByCondition(SearchCondition con);
    BoardDto getArticle(int articleNo) throws SQLException;
    int updateHit(int articleNo) throws SQLException;
    int modifyArticle(BoardDto boardDto) throws SQLException;
    int deleteArticle(int articleNo) throws SQLException;
    int writeComment(BoardCommentDto boardCommentDto) throws SQLException;
    List<BoardCommentDto> listComment(int boardNo) throws SQLException;
    int modifyComment(BoardCommentDto boardCommentDto) throws SQLException;
    int deleteComment(int commentNo) throws SQLException;

    int getBoardLikeById(BoardLikeDto likeInfo) throws SQLException; // 회원의 해당 게시글 좋아요 추가 여부
    int getNumBoardLikes(int boardNo) throws SQLException; // 해당 게시판의 좋아요 개수 불러오기
    int addBoardLike(BoardLikeDto likeInfo) throws SQLException; // 좋아요 add
    int deleteBoardLike(BoardLikeDto likeInfo) throws SQLException; // 좋아요 del
    List<BoardDto> getLikeList(String id)throws SQLException; // 좋아요 누른 모든 게시판의 리스트

}
