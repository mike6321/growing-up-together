package com.mission.dto.login;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
public class ReqLogin {

    @Email
    private String email;
    @Size(min = 3, max = 100)
    private String password;

}
