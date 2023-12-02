package com.sj.trip.login.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserFileDto {
    private String id;
    private String pwd;
    private String name;
    private String email;
    private String nickName;
    private String token;
    private byte[] profile;
    private String joinDate;
}
