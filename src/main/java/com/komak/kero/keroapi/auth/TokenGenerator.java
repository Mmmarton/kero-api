package com.komak.kero.keroapi.auth;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

class TokenGenerator {

  static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  static final String LOWER = UPPER.toLowerCase(Locale.ROOT);
  static final String DIGITS = "0123456789";
  static final String ALPHANUMERIC = UPPER + LOWER + DIGITS;

  private final Random random;
  private final char[] symbols;
  private final char[] buf;

  public TokenGenerator(int length, String symbols) {
    if (length < 1 || symbols.length() < 2) {
      throw new IllegalArgumentException();
    }
    this.random = Objects.requireNonNull(new SecureRandom());
    this.symbols = symbols.toCharArray();
    this.buf = new char[length];
  }

  /**
   * Generate a random string.
   */
  public String getToken() {
    for (int idx = 0; idx < buf.length; ++idx) {
      buf[idx] = symbols[random.nextInt(symbols.length)];
    }
    return new String(buf);
  }

}
