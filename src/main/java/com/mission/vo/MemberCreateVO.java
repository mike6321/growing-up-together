package com.mission.vo;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @NoArgsConstructor
@ToString @EqualsAndHashCode
@AllArgsConstructor
public class MemberCreateVO {

  @NotEmpty
  private String nickname;
  @Email
  private String email;
  private boolean topicOfInterestAlarm;

}
