package com.mission.domain;

import lombok.*;

import javax.persistence.*;

@Entity @Getter
@Table(name = "grade")
@NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Grade {

    @Id
    @GeneratedValue
    @Column(name = "grade_id")
    private Long id;
    @Embedded
    private GradeStaus gradeStaus;
    private long point;

}
