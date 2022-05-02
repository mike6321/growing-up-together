package com.mission.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "participation_mission")
@Getter
public class ParticipationMission {

    @Id @GeneratedValue
    @Column(name = "participation_mission_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_confirmation_id")
    private List<MissionConfirmation> missionConfirmations = new ArrayList();

    public void setMember(Member member) {
        this.member = member;
    }

}
