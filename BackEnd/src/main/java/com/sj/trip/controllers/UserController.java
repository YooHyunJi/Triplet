package com.sj.trip.controllers;

import com.sj.trip.chat.dto.ChatMessage;
import com.sj.trip.login.model.FileDto;
import com.sj.trip.login.model.UserDto;
import com.sj.trip.login.model.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@Slf4j
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/file/upload")
    public ResponseEntity<?> handlerFileUpload(@RequestParam("file") MultipartFile file, String id) throws SQLException {
        String uploadImage = null;
        HttpStatus status = HttpStatus.OK;
        log.debug("file : {}", file.getOriginalFilename());
        log.debug("id : {}", id);
        Map<String, Object> resultMap = new HashMap<>();

        int result = 0;
        try {
            if (id.equals("?")){
                resultMap.put("new-file", file.getBytes());
            }
            else{
                result = userService.uploadImage(file, id);
                if (result == 0)
                    status = HttpStatus.NO_CONTENT;
                else {
                    resultMap.put("new-file", file.getBytes());
                }
            }
        } catch (IOException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/file/download/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable("id") String id) {
        log.debug("id:{}",id);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        byte[] downloadImage = userService.downloadImage(id);
        resultMap.put("profile", downloadImage);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(downloadImage);
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

//    @PostMapping("file/download")
//    public ResponseEntity<?> downloadImageList(){
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status = HttpStatus.OK;
//
//
//    }

    @ApiOperation(value="닉네임 중복 체크", notes="닉네임 수정 시 중복 체크")
    @PostMapping("/check/nickname")
    public ResponseEntity<Map<String, Object>> nickNameCheck (
            @RequestBody @ApiParam(value = "변경하고 싶은 닉네임", required = true) String nickName){
        nickName = nickName.replaceAll("\"", "");
        log.debug("mypage nickNameCheck : {}", nickName);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            UserDto userDto = userService.checkNickName(nickName);
            if (userDto == null){
                resultMap.put("result", true);
            }
            else{
                resultMap.put("result", false);
                status = HttpStatus.NO_CONTENT;
            }
        } catch (SQLException e) {
            log.debug("닉네임 중복 체크 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 기존 비밀번호 체크 ( mypage )
    @ApiOperation(value = "비밀번호 체크", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
    @PostMapping("/check/pwd")
    public ResponseEntity<Map<String, Object>> checkPwd(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) UserDto userDto) {
        log.debug("login user : {}", userDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            UserDto loginUser = userService.login(userDto);
            if(loginUser != null) {
                status = HttpStatus.OK;
            } else {
                log.debug("기존 패스워드가 일치하지않습니다.");
                status = HttpStatus.NO_CONTENT;
            }

        } catch (Exception e) {
            log.debug("로그인 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 비밀번호 변경 api
    @ApiOperation(value = "비밀번호 변경", notes = "아이디를 통해 비번 변경")
    @PostMapping("/modify/pwd")
    public ResponseEntity<?> changePwd(@PathVariable ("id") String id, @PathVariable ("newPwd") String newPwd) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.CREATED;
        int result = 0;
        try {
            result = userService.changePwd(id, newPwd);
            if(result == 0) {
                status = HttpStatus.NO_CONTENT;
            }

        } catch (Exception e) {
            log.debug("패스워드 변경 실패 : {}", e);
//            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(result, status);
    }
    // 닉네임 변경 api
    @ApiOperation(value = "닉네임 변경", notes = "아이디를 통해 닉네임 변경")
    @PostMapping("/modify/nickname")
    public ResponseEntity<?> changeNickName(@RequestBody UserDto userDto) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.CREATED;
        int result = 0;
        try {
            result = userService.changeNickName(userDto.getId(), userDto.getNickName());
            if(result == 0) {
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            log.debug("패스워드 변경 실패 : {}", e);
//            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(result, status);
    }
    @ApiOperation(value = "닉네임 찾기", notes = "아이디를 통해 닉네임 변경")
    @PostMapping("/find/nickname")
    public ResponseEntity<?> findNickname(@RequestBody String id) throws SQLException {
        id = id.replaceAll("\"", "");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        byte[] downloadImage = userService.downloadImage(id);
        String result = userService.findNickname(id);
        resultMap.put("profile", downloadImage);
        resultMap.put("nickName", result);
        if (result != null){
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
