package com.mission.dto.member;

import com.mission.domain.Grade;
import com.mission.domain.Member;
import com.mission.domain.MemberOfTopicInterest;
import com.mission.domain.ParticipationMission;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
@AllArgsConstructor
@ToString @EqualsAndHashCode
public class ResFindMember {

  private Long id;
  private String nickname;
  private boolean isTopicOfInterestAlarm;
  private String profileImageUrl;
  private boolean isEmailAuthenticate;
  private String email;
  private List<ParticipationMission> participationMissions = new ArrayList<>();
  private boolean isWithdrawal;
  private List<MemberOfTopicInterest> topicOfInterests = new ArrayList<>();
  private Grade grade;

  public static ResFindMember of (final Member member) {
    return new ResFindMember(member.getId(),
      member.getNickname(),
      member.isTopicOfInterestAlarm(),
      member.getProfileImageUrl(),
      member.isEmailAuthenticate(),
      member.getEmail(),
      member.getParticipationMissions(),
      member.isWithdrawal(),
      member.getTopicOfInterests(),
      member.getGrade()
    );
  }

}
