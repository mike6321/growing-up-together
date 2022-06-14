package com.mission.dto.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
@ToString @EqualsAndHashCode
@SuperBuilder
public class ReqCreateMember {

  @NotEmpty
  private String nickname;
  @Email
  private String email;
  @NotNull
  private boolean topicOfInterestAlarm;
  @NotNull
  private List<String> topicOfInterests = new ArrayList<>();
  @Size(min = 3, max = 100)
  private String password;

  public void encodePassword(String password) {
    this.password = password;
  }

}
