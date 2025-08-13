package org.example.be.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;
import java.util.List;

public class QuestionsLoader {
    private static final String YAML_FILE = "/questions.yml";

    public static List<Question> loadQuestions() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream inputStream = QuestionsLoader.class.getResourceAsStream(YAML_FILE)) {
            QuestionList data = mapper.readValue(inputStream, QuestionList.class);
            return data.getQuestions();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки YAML-файла", e);
        }
    }

    private static class QuestionList {
        private List<Question> questions;
        public List<Question> getQuestions() { return questions; }
        public void setQuestions(List<Question> questions) { this.questions = questions; }
    }

    public static class Question {
        private String question;
        private String answer;

        public String getQuestion() { return question; }
        public String getAnswer() { return answer; }
    }
}
