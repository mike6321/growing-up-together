package com.mission.domain;

import lombok.*;

import javax.persistence.*;

@Entity @Getter
@Table(name = "grade")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Grade {

    @Id
    @GeneratedValue
    @Column(name = "grade_id")
    private Long id;
    @Column(name = "grade_status")
    @Enumerated(EnumType.STRING)
    private GradeStaus gradeStaus;
    @Column(name = "point")
    private long point;

    public void addPoint(long point) {
        this.point += point;

    }

    public void updateStatus(GradeStaus gradeStaus) {
        this.gradeStaus = gradeStaus;

    }
}
