package com.mission.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqLogin {

    @Email
    private String email;
    @Size(min = 3, max = 100)
    private String password;

}
