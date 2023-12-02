package com.sj.trip.login.model.service;

import com.sj.trip.login.model.FileDto;
import com.sj.trip.login.model.UserDto;
import com.sj.trip.login.model.UserFileDto;
import com.sj.trip.login.model.mapper.UserMapper;
import com.sj.trip.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        super();
        this.userMapper = userMapper;
    }

    @Override
    public UserDto login(UserDto userDto) throws SQLException {
        return userMapper.login(userDto);
    }

    @Override
    public UserFileDto userInfo(String id) throws SQLException {
        UserFileDto userDto = userMapper.userInfo(id);
//        FileDto fileDto = userDto.getProfile();
        if (userDto == null)
            return null;
        if (userDto.getProfile() != null)
            userDto.setProfile(ImageUtil.decompressImage(userDto.getProfile()));
        return userDto;
    }

    @Override
    public int userUpdate(UserDto userDto) throws SQLException {
        return userMapper.userUpdate(userDto);
    }

    @Override
    public void saveRefreshToken(String id, String refreshToken) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("token", refreshToken);
        userMapper.saveRefreshToken(map);
    }


    @Override
    public Object getRefreshToken(String id) throws SQLException {
        return userMapper.getRefreshToken(id);
    }

    @Override
    public void deleteRefreshToken(String id) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("token", null);
        userMapper.deleteRefreshToken(map);
    }

    @Override
    public int signUp(UserDto userDto) throws SQLException {
        return userMapper.signUp(userDto);
    }

    @Override
    public UserDto checkNickName(String nickName) throws SQLException {
        return userMapper.checkNickName(nickName);
    }

    @Override
    public int uploadImage(MultipartFile file, String id)  throws IOException {
        FileDto fileDto = new FileDto();
        fileDto.setId(id);
        fileDto.setProfile(ImageUtil.compressImage(file.getBytes()));

//        int result = userMapper.uploadImage(fileDto);
//        if (result == 1)
//            return file.
        return userMapper.uploadImage(fileDto);
    }

    @Override
    public byte[] downloadImage(String id) {
        FileDto fileDto = userMapper.downloadImage(id);
        if (fileDto == null) return null;
        if (fileDto.getProfile() != null)
            return ImageUtil.decompressImage(fileDto.getProfile());
        else return null;
    }

    @Override
    public int changePwd(String id, String newPwd) throws SQLException {
        return userMapper.changePwd(id, newPwd);
    }

    @Override
    public int changeNickName(String id, String nickName) throws SQLException {
        return userMapper.changeNickName(id, nickName);
    }

    @Override
    public String findNickname(String id) throws SQLException {
        return userMapper.findNickname(id);
    }

    @Override
    public String findId(String nickName) throws SQLException {
        return userMapper.findId(nickName);
    }
}
