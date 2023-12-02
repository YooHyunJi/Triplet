package com.sj.trip.login.model.mapper;

import com.sj.trip.login.model.FileDto;
import com.sj.trip.login.model.UserDto;
import com.sj.trip.login.model.UserFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface UserMapper {
    UserDto login(UserDto userDto) throws SQLException;
    UserFileDto userInfo(String id) throws SQLException;
    int userUpdate(UserDto userDto) throws SQLException;
    void saveRefreshToken(Map<String, String> map) throws SQLException;
    Object getRefreshToken(String id) throws SQLException;
    void deleteRefreshToken(Map<String, String> map) throws SQLException;
    int signUp(UserDto userDto) throws SQLException;
    UserDto checkNickName(String nickName) throws SQLException;
    int uploadImage(FileDto fileDto) throws IOException;
    FileDto downloadImage(String id);
    int changePwd(String id, String newPwd) throws SQLException;
    int changeNickName(String id, String nickName) throws SQLException;
    String findNickname(String id) throws SQLException;
    String findId(String nickName) throws SQLException;
}
