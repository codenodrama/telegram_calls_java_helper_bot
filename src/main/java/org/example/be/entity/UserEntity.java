package org.example.be.entity;

import java.util.List;
import java.util.Objects;

public class UserEntity {
    private final Long userId;
    private final String firstName;
    private final String lastName;

    private final Long chatId;

    public UserEntity(Long userId, String firstName, String lastName, Long chatId) {
        this.userId = userId;
        this.firstName = Objects.requireNonNullElse(firstName, "");
        this.lastName = Objects.requireNonNullElse(lastName, "");
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, chatId);
    }

    public Long getUserId() { return userId; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getChatId() {
        return chatId;
    }
}
