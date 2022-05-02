package com.mission.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mission_confirmation")
@Getter
public class MissionConfirmation {

    @Id @GeneratedValue
    @Column(name = "mission_confirmation_id")
    private Long id;
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
    @Column(name = "upload_image_url")
    private String uploadImageUrl;
    @Column(name = "summary")
    private String summary;
    @Column(name = "is_mission_confirmation")
    private boolean isMissionConfirmation;

}
