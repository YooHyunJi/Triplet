package com.sj.trip.board.model.service;

import com.sj.trip.board.dto.BoardCommentDto;
import com.sj.trip.board.dto.BoardDto;
import com.sj.trip.board.dto.BoardLikeDto;
import com.sj.trip.board.dto.SearchCondition;
import com.sj.trip.board.model.repo.BoardRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final BoardRepo boardRepo;
    @Override
    @Transactional
    public int writeArticle(BoardDto boardDto) throws SQLException {
        return boardRepo.writeArticle(boardDto);
    }

    @Override
    public List<BoardDto> listArticle() throws SQLException {
        return boardRepo.listArticle();
    }

    @Override
    public Map<String, Object> listArticlePg(Map<String, String> map) throws SQLException {

        log.info(map.toString());

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("content", map.get("content") == null ? "" : map.get("content"));
        int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : (String) map.get("pgno"));
        int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : (String) map.get("spp"));
        int start = currentPage * sizePerPage - sizePerPage;

        param.put("start", start);
        param.put("listsize", sizePerPage);
        log.debug("content : {}",param.get("content"));
        String condition = map.get("condition");
        param.put("condition", condition == null ? "" : condition);
        List<BoardDto> list = boardRepo.listArticlePg(param);

        int totalArticleCount = boardRepo.getTotalArticleCount(param);
        int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("list", list);
        resultMap.put("currentPage", currentPage);
        resultMap.put("totalPageCount", totalPageCount);
        return resultMap;
    }

//    @Override
//    public BoardListDto listArticle(Map<String, String> map) throws Exception {
//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("word", map.get("word") == null ? "" : map.get("word"));
//        int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
//        int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
//        int start = currentPage * sizePerPage - sizePerPage;
//        param.put("start", start);
//        param.put("listsize", sizePerPage);
//
//        String key = map.get("key");
//        param.put("key", key == null ? "" : key);
//        if ("user_id".equals(key))
//            param.put("key", key == null ? "" : "b.user_id");
//        List<BoardDto> list = boardMapper.listArticle(param);
//
//        if ("user_id".equals(key))
//            param.put("key", key == null ? "" : "user_id");
//        int totalArticleCount = boardMapper.getTotalArticleCount(param);
//        int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
//
//        BoardListDto boardListDto = new BoardListDto();
//        boardListDto.setArticles(list);
//        boardListDto.setCurrentPage(currentPage);
//        boardListDto.setTotalPageCount(totalPageCount);
//
//        return boardListDto;
//    }

    @Override
    public List<BoardDto> searchByCondition(SearchCondition con) {
        return boardRepo.searchByCondition(con);
    }


    @Override
    public BoardDto getArticle(int articleNo) throws SQLException {
        return boardRepo.getArticle(articleNo);
    }

    @Override
    @Transactional
    public int updateHit(int articleNo) throws SQLException {
        return boardRepo.updateHit(articleNo);
    }

    @Override
    @Transactional
    public int modifyArticle(BoardDto boardDto) throws SQLException {
        return boardRepo.modifyArticle(boardDto);
    }

    @Override
    public int deleteArticle(int articleNo) throws SQLException {
        return boardRepo.deleteArticle(articleNo);
    }

    @Override
    public int writeComment(BoardCommentDto boardCommentDto) throws SQLException {
        return boardRepo.writeComment(boardCommentDto);
    }

    @Override
    public List<BoardCommentDto> listComment(int boardNo) throws SQLException {
        return boardRepo.listComment(boardNo);
    }

    @Override
    public int modifyComment(BoardCommentDto boardCommentDto) throws SQLException {
        return boardRepo.modifyComment(boardCommentDto);
    }

    @Override
    public int deleteComment(int commentNo) throws SQLException {
        return boardRepo.deleteComment(commentNo);
    }

    @Override
    public int getBoardLikeById(BoardLikeDto likeInfo) throws SQLException {
        BoardLikeDto boardLikeDto = boardRepo.getBoardLikeById(likeInfo.getId(), likeInfo.getBoardNo());
        if (boardLikeDto != null) return 1;
        else return 0;
    }

    @Override
    public int getNumBoardLikes(int boardNo) throws SQLException {
        Integer result = boardRepo.getNumBoardLikes(boardNo);
        if (result == null)
            result = 0;
        return result;
    }

    @Override
    public int addBoardLike(BoardLikeDto likeInfo) throws SQLException {
        return boardRepo.addBoardLike(likeInfo);
    }

    @Override
    public int deleteBoardLike(BoardLikeDto likeInfo) throws SQLException {
        return boardRepo.deleteBoardLike(likeInfo);
    }

    @Override
    public List<BoardDto> getLikeList(String id) throws SQLException {
        return boardRepo.getLikeList(id);
    }
}
