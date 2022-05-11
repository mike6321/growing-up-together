package com.mission.vo;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
@ToString @EqualsAndHashCode
@AllArgsConstructor
public class MemberCreateVO {

  @NotEmpty
  private String nickname;
  @Email
  private String email;
  private boolean topicOfInterestAlarm;
  private List<String> topicOfInterests = new ArrayList();

}
