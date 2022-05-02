package com.mission.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "grade")
@Getter
public class Grade {

    @Id
    @GeneratedValue
    @Column(name = "grade_id")
    private Long id;
    @Embedded
    private GradeStaus gradeStaus;
    private long point;

}
