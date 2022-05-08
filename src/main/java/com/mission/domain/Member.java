package com.mission.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mission.vo.MemberCreateVO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "is_topic_of_interest_alarm")
    private boolean isTopicOfInterestAlarm;
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    @Column(name = "is_email_authenticate")
    private boolean isEmailAuthenticate;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<ParticipationMission> participationMissions = new ArrayList();
    @Column(name = "is_withdrawal")
    private boolean isWithdrawal;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    @JoinColumn(name = "member_of_topic_of_interest_id")
    @Builder.Default
    private List<MemberOfTopicInterest> topicOfInterests = new ArrayList();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id")
    @JsonIgnore
    private Grade grade;

    public static Member createMember(MemberCreateVO memberCreateVO, Grade memberGrade) {
        return Member.builder()
          .email(memberCreateVO.getEmail())
          .nickname(memberCreateVO.getNickname())
          .isTopicOfInterestAlarm(memberCreateVO.isTopicOfInterestAlarm())
          .grade(memberGrade)
          .build();
    }

    public void addParticipationMission(ParticipationMission participationMission) {
        participationMissions.add(participationMission);
        participationMission.setMember(this);

    }

    public void addTopicOfInterests(MemberOfTopicInterest memberOfTopicInterest) {
        topicOfInterests.add(memberOfTopicInterest);
        memberOfTopicInterest.setMember(this);

    }

    /* Setter */
    public void updateNickname(String nickname) {
        this.nickname = nickname;

    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;

    }

    public void updateIsTopicOfInterestAlarm(boolean isTopicOfInterestAlarm) {
        this.isTopicOfInterestAlarm = isTopicOfInterestAlarm;

    }

    public void updateIsEmailAuthenticate(boolean isEmailAuthenticate) {
        this.isEmailAuthenticate = isEmailAuthenticate;

    }

    public void updateEmail(String email) {
        this.email = email;

    }

    public void updateIsWithdrawal(boolean isWithdrawal) {
        this.isWithdrawal = isWithdrawal;

    }

}
