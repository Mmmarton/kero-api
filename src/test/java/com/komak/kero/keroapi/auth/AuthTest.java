package com.komak.kero.keroapi.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.komak.kero.keroapi.TestBase;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

public class AuthTest extends TestBase {

  @Test
  public void testLogin_whenIncorrectCredentials_fails() throws Exception {
    Credentials credentials = new Credentials();
    MockHttpServletResponse result = getMockMvc().perform(
        post("/auth/login").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(credentials))
    ).andReturn().getResponse();

    assertThat(result.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    assertThat(result.getContentAsString()).isEqualTo("Invalid credentials.");
  }

  @Test
  public void testGetSession_success() throws Exception {
    MockHttpServletResponse result = getMockMvc().perform(get("/auth/session")
        .with(csrf())).andReturn().getResponse();

    assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(result.getContentAsString()).isEqualTo("Here you are.");
  }

  @Test
  public void testGetStatus_whenNotLoggedIn_fails() throws Exception {
    MockHttpServletResponse result = getMockMvc().perform(get("/auth/status")
        .with(csrf())).andReturn().getResponse();

    assertThat(result.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    assertThat(result.getContentAsString()).isEqualTo("Authentication failed.");
  }
}