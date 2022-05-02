package com.mission.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
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
    private List<ParticipationMission> participationMissions = new ArrayList();
    @Column(name = "is_withdrawal")
    private boolean isWithdrawal;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_of_topic_of_interest_id")
    private List<MemberOfTopicInterest> topicOfInterests = new ArrayList();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    public void addParticipationMission(ParticipationMission participationMission) {
        participationMissions.add(participationMission);
        participationMission.setMember(this);
    }

}
