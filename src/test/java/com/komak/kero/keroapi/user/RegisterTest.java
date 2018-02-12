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
    user.setRole(null);

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
        new FieldErrorMessage("role", FieldErrorMessage.NULL));
  }

  @Test
  public void testRegister_withShortFields_fails() throws Exception {
    UserCreateModel user = createUserCreateModel(7, 11, 2, 4);

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
        new FieldErrorMessage("email", errorMessage));
  }

  @Test
  public void testRegister_withLongFields_fails() throws Exception {
    UserCreateModel user = createUserCreateModel(31, 31, 21, 51);

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
        new FieldErrorMessage("email", errorMessage));
  }

  @Test
  public void testRegister_success() throws Exception {
    UserCreateModel user = createUserCreateModel(20, 20, 10, 30);

    MockHttpServletResponse result = getMockMvc().perform(
        post("/user/register").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(user))
    ).andReturn().getResponse();

    assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(result.getContentAsString()).isEqualTo("Done.");
  }

  @Test
  public void testRegister_withDuplicateUsername_fails() throws Exception {
    UserCreateModel user = createUserCreateModel(20, 20, 10, 30);

    MockHttpServletResponse result = getMockMvc().perform(
        post("/user/register").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(user))
    ).andReturn().getResponse();

    assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(result.getContentAsString()).isEqualTo("Done.");

    user.setEmail(RandomStringUtils.random(20));

    result = getMockMvc().perform(
        post("/user/register").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(user))
    ).andReturn().getResponse();

    assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(toObject(result.getContentAsString(), FieldErrorMessage.class))
        .isEqualTo(new FieldErrorMessage("username", FieldErrorMessage.DUPLICATE));
  }

  @Test
  public void testRegister_withDuplicateEmail_fails() throws Exception {
    UserCreateModel user = createUserCreateModel(20, 20, 10, 30);

    MockHttpServletResponse result = getMockMvc().perform(
        post("/user/register").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(user))
    ).andReturn().getResponse();

    assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(result.getContentAsString()).isEqualTo("Done.");

    user.setUsername(RandomStringUtils.random(20));

    result = getMockMvc().perform(
        post("/user/register").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(user))
    ).andReturn().getResponse();

    assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(toObject(result.getContentAsString(), FieldErrorMessage.class))
        .isEqualTo(new FieldErrorMessage("email", FieldErrorMessage.DUPLICATE));
  }

  @Test
  public void testLogin_whenRegistered_success() throws Exception {
    UserCreateModel user = createUserCreateModel(20, 20, 10, 30);

    MockHttpServletResponse result = getMockMvc().perform(
        post("/user/register").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(user))
    ).andReturn().getResponse();

    assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(result.getContentAsString()).isEqualTo("Done.");

    Credentials credentials = new Credentials();
    credentials.setUsername(user.getUsername());
    credentials.setPassword(user.getPassword());

    result = getMockMvc().perform(
        post("/auth/login").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(toJson(credentials))
    ).andReturn().getResponse();

    UserViewModel actual = toObject(result.getContentAsString(), UserViewModel.class);
    UserViewModel expected = UserAdapter.toViewModel(user);
    expected.setToken(actual.getToken());
    assertThat(actual).isEqualTo(expected);

    printResult(result);
  }

  private UserCreateModel createUserCreateModel(int... lengths) {
    UserCreateModel user = new UserCreateModel();
    user.setUsername(RandomStringUtils.random(lengths[0]));
    user.setPassword(RandomStringUtils.random(lengths[1]));
    user.setNickname(RandomStringUtils.random(lengths[2]));
    user.setEmail(RandomStringUtils.random(lengths[3]));
    user.setRole(1);
    return user;
  }
}