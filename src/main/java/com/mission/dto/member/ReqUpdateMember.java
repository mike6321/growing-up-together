package com.mission.dto.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @NoArgsConstructor
@ToString @EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ReqUpdateMember extends ReqCreateMember{

  @Min(value = 0L) @Max(value = Long.MAX_VALUE)
  @NotNull
  private Long id;

}
