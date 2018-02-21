package com.komak.kero.keroapi.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.komak.kero.keroapi.TestBase;
import com.komak.kero.keroapi.auth.Credentials;
import com.komak.kero.keroapi.validation.FieldErrorMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

public class RegisterTest extends TestBase {

  @Test
  public void testRegister_withEmptyFields_fails() throws Exception {
    UserCreateModel user = new UserCreateModel();

    MockHttpServletResponse result = getMockMvc().perform(
        post("/user/register").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(user))
    ).andReturn().getResponse();

    String errorMessage = FieldErrorMessage.EMPTY;
    assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    FieldErrorMessage[] fieldErrors = toObject(result.getContentAsString(),
        FieldErrorMessage[].class);
    assertThat(fieldErrors).containsExactlyInAnyOrder(
        new FieldErrorMessage("username", errorMessage),
        new FieldErrorMessage("password", errorMessage),
        new FieldErrorMessage("nickname", errorMessage),
        new FieldErrorMessage("email", errorMessage),
        new FieldErrorMessage("inviteCode", errorMessage));
  }

  @Test
  public void testRegister_withShortFields_fails() throws Exception {
    UserCreateModel user = createUserCreateModel(7, 11, 2, 4, 4);

    MockHttpServletResponse result = getMockMvc().perform(
        post("/user/register").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(user))
    ).andReturn().getResponse();

    String errorMessage = FieldErrorMessage.INVALID_LENGTH;
    assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    FieldErrorMessage[] fieldErrors = toObject(result.getContentAsString(),
        FieldErrorMessage[].class);
    assertThat(fieldErrors).containsExactlyInAnyOrder(
        new FieldErrorMessage("username", errorMessage),
        new FieldErrorMessage("password", errorMessage),
        new FieldErrorMessage("nickname", errorMessage),
        new FieldErrorMessage("email", errorMessage),
        new FieldErrorMessage("inviteCode", errorMessage));
  }

  private UserCreateModel createUserCreateModel(int... lengths) {
    UserCreateModel user = new UserCreateModel();
    user.setUsername(RandomStringUtils.random(lengths[0]));
    user.setPassword(RandomStringUtils.random(lengths[1]));
    user.setNickname(RandomStringUtils.random(lengths[2]));
    user.setEmail(RandomStringUtils.random(lengths[3]));
    user.setInviteCode(RandomStringUtils.random(lengths[4]));
    return user;
  }
}