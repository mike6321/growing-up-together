package com.mission.dto.member;

import com.mission.domain.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
  private List<String> participationMissions = new ArrayList<>();
  private boolean isWithdrawal;
  private List<String> topicOfInterests = new ArrayList<>();
  private String grade;
  private Long point;

  public static ResFindMember of (final Member member) {
    return new ResFindMember(member.getId(),
      member.getNickname(),
      member.isTopicOfInterestAlarm(),
      member.getProfileImageUrl(),
      member.isEmailAuthenticate(),
      member.getEmail(),
      member.getParticipationMissions()
        .stream()
        .map(ParticipationMission::getMission)
        .map(Mission::getSubject)
        .collect(Collectors.toList()),
      member.isWithdrawal(),
      member.getTopicOfInterests()
        .stream()
        .map(MemberOfTopicInterest::getTopicOfInterest)
        .map(TopicOfInterest::getName)
        .collect(Collectors.toList()),
      member.getGrade().getGradeStaus().toString(),
      member.getGrade().getPoint()
    );
  }

}
