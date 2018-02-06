package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.validation.FieldErrorMessage;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

class UserCreateModel {

    @NotEmpty(message = FieldErrorMessage.EMPTY)
    @Length(min = 8, max = 30, message = FieldErrorMessage.INVALID_LENGTH)
    private String username;
    @NotEmpty(message = FieldErrorMessage.EMPTY)
    @Length(min = 12, max = 30, message = FieldErrorMessage.INVALID_LENGTH)
    private String password;
    @NotEmpty(message = FieldErrorMessage.EMPTY)
    @Length(min = 3, max = 20, message = FieldErrorMessage.INVALID_LENGTH)
    private String nickname;
    @NotEmpty(message = FieldErrorMessage.EMPTY)
    @Length(min = 5, max = 50, message = FieldErrorMessage.INVALID_LENGTH)
    private String email;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setEmail(email);
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
