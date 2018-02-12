package com.komak.kero.keroapi.auth;

public enum Role {
  ROLE_ADMIN,
  ROLE_MEMBER,
  ROLE_GUEST;

  public static Role fromIndex(int i) {
    switch (i) {
      case 0:
        return ROLE_ADMIN;
      case 1:
        return ROLE_MEMBER;
      default:
        return ROLE_GUEST;
    }
  }
}
