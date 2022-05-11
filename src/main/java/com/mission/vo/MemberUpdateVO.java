package com.mission.vo;

import com.mission.domain.Grade;
import com.mission.domain.MemberOfTopicInterest;
import com.mission.domain.ParticipationMission;
import lombok.*;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
@ToString @EqualsAndHashCode
@AllArgsConstructor
public class MemberUpdateVO {

  @Min(value = 1L) @Max(value = Long.MAX_VALUE)
  @NotNull
  private Long id;
  @NotEmpty
  private String nickname;
  private boolean topicOfInterestAlarm;
  private String profileImageUrl;
  private boolean isEmailAuthenticate;
  private boolean isWithdrawal;
  private Grade grade;
  @Email
  private String email;
  private List<String> topicOfInterests = new ArrayList();

}
