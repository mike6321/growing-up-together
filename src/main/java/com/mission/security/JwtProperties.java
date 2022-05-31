package com.mission.security;

public enum JwtProperties {

  SECRET_KEY("12345678901234567890123456789012")
  , HEADER_NAME("Authorization")
  , TOKEN_PREFIX("Bearer ")
  , AUTHORIZATION_KEY("auth")
  ;

  private String value;
  JwtProperties(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return getValue();
  }

}
