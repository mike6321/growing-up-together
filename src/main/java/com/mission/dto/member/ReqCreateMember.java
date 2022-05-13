package com.mission.dto.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

}
