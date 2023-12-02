package com.sj.trip.login.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value = "UserDto : 회원 정보", description = "회원의 상세 정보를 나타낸다.")
public class UserDto {
    private String id;
    private String pwd;
    private String name;
    private String email;
    private String nickName;
    private String token;
//    private FileDto profile;
    private String joinDate;

}
