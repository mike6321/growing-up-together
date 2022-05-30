package com.mission.domain;

import com.mission.domain.common.BaseTimeEntity;
import com.mission.dto.member.ReqCreateMember;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity @Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicUpdate @Builder
public class Member extends BaseTimeEntity implements UserDetails {

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
    private List<ParticipationMission> participationMissions = new ArrayList<>();
    @Column(name = "is_withdrawal")
    private boolean isWithdrawal;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MemberOfTopicInterest> topicOfInterests = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id")
    private Grade grade;
    @Column(name = "password")
    private String password;
//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<String> roles = new ArrayList<>();

    public static Member createMember(ReqCreateMember memberCreateVO, List<MemberOfTopicInterest> topicOfInterests, PasswordEncoder passwordEncoder) {
    final Member createMember =
        Member.builder()
            .email(memberCreateVO.getEmail())
            .nickname(memberCreateVO.getNickname())
            .isTopicOfInterestAlarm(memberCreateVO.isTopicOfInterestAlarm())
            .grade(Grade.createBeginnerGrade())
            .password(passwordEncoder.encode(memberCreateVO.getPassword()))
            .build();
        topicOfInterests.forEach(createMember::addTopicOfInterests);
        return createMember;
    }

    public void addParticipationMission(ParticipationMission participationMission) {
        participationMissions.add(participationMission);
        participationMission.setMember(this);
    }

    public void addTopicOfInterests(MemberOfTopicInterest memberOfTopicInterest) {
        topicOfInterests.add(memberOfTopicInterest);
        memberOfTopicInterest.setMember(this);
    }

    public void deleteTopicOfInterests() {
        topicOfInterests.forEach(memberOfTopicInterest -> memberOfTopicInterest.setMember(null));
        topicOfInterests = new ArrayList<>();

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
