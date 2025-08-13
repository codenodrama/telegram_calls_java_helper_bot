package org.example.be.entity;

public class QuestionEntity {
    private String answer;
    private String question;

    public String getAnswer() { return answer; }

    public String getQuestion() { return question; }

    public QuestionEntity(String answer, String question) {
        this.answer = answer;
        this.question = question;
    }

}
