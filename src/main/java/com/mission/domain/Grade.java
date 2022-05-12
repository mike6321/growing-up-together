package com.mission.domain;

import com.mission.domain.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity @Getter
@Table(name = "grade")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicUpdate @Builder
public class Grade extends BaseTimeEntity {

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

    public static Grade createBeginnerGrade() {
        return Grade
          .builder()
          .point(0L)
          .gradeStaus(GradeStaus.BEGINNER)
          .build();
    }

}
