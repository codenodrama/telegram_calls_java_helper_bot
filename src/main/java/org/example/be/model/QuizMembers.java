package org.example.be.model;

import org.example.be.entity.UserEntity;

import java.util.*;
import java.util.stream.Collectors;

public class QuizMembers {
    public static final HashMap<Long, HashSet<UserEntity>> QUIZ_MEMBERS = new HashMap<>();

    public void addMember(Long groupId, UserEntity user) {
        QUIZ_MEMBERS.get(groupId).add(user);
    }

    public void clear(Long groupId) {
        QUIZ_MEMBERS.get(groupId).clear();
    }

    public static List<String> getUserList(Long chatId) {
        return QUIZ_MEMBERS.get(chatId).stream()
                .map(userEntity ->
                        userEntity.getFirstName() == null ? "" : userEntity.getLastName())
                .collect(Collectors.toList());
    }
}
