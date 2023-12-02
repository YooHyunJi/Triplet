package com.sj.trip.controllers;

import com.sj.trip.board.dto.BoardCommentDto;
import com.sj.trip.board.dto.BoardDto;
import com.sj.trip.board.dto.BoardLikeDto;
import com.sj.trip.board.dto.SearchCondition;
import com.sj.trip.board.model.service.BoardService;
import com.sj.trip.login.model.service.UserService;
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
@RequestMapping("/board")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    @GetMapping("/list")
    public ResponseEntity<?> list() throws SQLException {
        List<BoardDto> list = boardService.listArticle();
        if (list != null && !list.isEmpty()){
            //json
            return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    //{pgno: 1, spp: '20', key: 'article_no', word: '2'}
    @GetMapping("/search")
    public ResponseEntity<?> search(SearchCondition con) throws SQLException {
        System.out.println(con.toString());
        log.debug(con.toString());
        List<BoardDto> list = boardService.searchByCondition(con);
        if (list != null){
            return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/list/{articleNo}")
    public ResponseEntity<?> view(@PathVariable int articleNo) throws SQLException {
        BoardDto board = boardService.getArticle(articleNo);
        int result = boardService.updateHit(articleNo);
        if (board != null && result == 1)
            return new ResponseEntity<BoardDto>(board, HttpStatus.OK);
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/write")
    public ResponseEntity<?> myWrite(@RequestBody BoardDto boardDto) throws SQLException {
        int result = boardService.writeArticle(boardDto);
        log.debug(boardDto.toString());
        if (result == 1)
            return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @PutMapping ("/modify")
    public ResponseEntity<?> myModify(@RequestBody BoardDto boardDto) throws SQLException {
        int result = boardService.modifyArticle(boardDto);
        if (result == 1)
            return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/delete/{articleNo}")
    public ResponseEntity<?> delete(@PathVariable int articleNo) throws SQLException {
        int result = boardService.deleteArticle(articleNo);
        if (result == 1)
            return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    //댓글 관련 controller
    @PostMapping("/write/comment")
    public ResponseEntity<?> writeComment(@RequestBody BoardCommentDto boardCommentDto) throws SQLException {
        log.debug(boardCommentDto.toString());
        int result = boardService.writeComment(boardCommentDto);
        if (result == 1)
            return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/list/comment/{boardNo}")
    public ResponseEntity<?> listComment(@PathVariable int boardNo) throws SQLException{
        //board추가
        List<BoardCommentDto> list = boardService.listComment(boardNo);
        if (list != null && !list.isEmpty()){
            return new ResponseEntity<List<BoardCommentDto>>(list, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @PutMapping ("/modify/comment")
    public ResponseEntity<?> modifyComment(@RequestBody BoardCommentDto boardCommentDto) throws SQLException {
        int result = boardService.modifyComment(boardCommentDto);
        if (result == 1)
            return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/delete/comment/{commentNo}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentNo) throws SQLException {
        int result = boardService.deleteComment(commentNo);
        if (result == 1)
            return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    // 회원의 해당 게시물 좋아요 추가 여부
    @PostMapping("/info/like")
    public ResponseEntity<?> isLike(@RequestBody BoardLikeDto likeDto){
        log.debug("isLike : {}", likeDto.toString());
        HttpStatus status = HttpStatus.OK;
        int result = 0;
        try {
            result = boardService.getBoardLikeById(likeDto);
            if (result == 0)
                status = HttpStatus.NO_CONTENT;
        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(result, status);
    }


    // 해당 게시글의 좋아요 개수 불러오기
    @GetMapping("/info/cnt/{boardNo}")
    public ResponseEntity<?> getCntBoardLike(@PathVariable("boardNo") int boardNo){
        log.debug("getCntBoardLike : {}", boardNo);
        HttpStatus status = HttpStatus.OK;
        int result = 0;
        try {
            result = boardService.getNumBoardLikes(boardNo);
        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(result, status);
    }

    // 좋아요 추가
    @PostMapping("/addlike")
    public ResponseEntity<?> addLike(@RequestBody BoardLikeDto boardLikeDto){
        log.debug("addlike : {}", boardLikeDto.toString());
        HttpStatus status = HttpStatus.OK;
        int result = 0;
        try {
            result = boardService.addBoardLike(boardLikeDto);
            if (result == 0)
                status = HttpStatus.NO_CONTENT;
        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(result, status);
    }

    // 좋아요 삭제

    @PostMapping("/dellike")
    public ResponseEntity<?> delLike(@RequestBody BoardLikeDto boardLikeDto) {
        log.debug("delLike : {}", boardLikeDto.toString());
        int result = 0;
        HttpStatus status = HttpStatus.OK;
        try {
            result = boardService.deleteBoardLike(boardLikeDto);
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
            List<BoardDto> likelist = boardService.getLikeList(id);
            resultMap.put("like-list", likelist);
            if (likelist == null){
                status = HttpStatus.NO_CONTENT;
            }
        } catch (SQLException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity< Map<String, Object>>(resultMap, status);
    }


    @GetMapping("/list/pg")
    public ResponseEntity<?> listArticlePg( @RequestParam Map<String, String> map) {
        log.info("listArticle map alalall - {}", map);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
//            log.info("l1111- {}", map);
            if(map.get("condition").equals("3")){
//                log.info("test {}", userService.findId(map.remove("content")));
                String nickName = userService.findId(map.get("content"));
                map.remove("content");
                map.put("content", nickName);
                log.info("2222222 - {}", map);
            }
            else{
                System.out.println("condition");
            }
            resultMap = boardService.listArticlePg(map);
//            System.out.println(resultMap.toString());
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

//        log.info("resultMAp :{}", resultMap);
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
