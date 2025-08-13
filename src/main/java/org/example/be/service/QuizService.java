package org.example.be.service;

import org.example.be.entity.QuestionEntity;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class QuizService {
    private static final String YAML_PATH = "/questions.yml";

    private static final Random RANDOM = new Random();

    private List<QuestionEntity> generateData() {
        Yaml yaml = new Yaml();
        try (InputStream input = QuizService.class.getResourceAsStream(YAML_PATH)) {
            if (input == null) {
                throw new FileNotFoundException("Файл не найден по пути: " + YAML_PATH);
            }

            Map<String, List<Map<String, Object>>> data = yaml.load(input);

            return data.getOrDefault("questions", Collections.emptyList())
                    .stream()
                    .map(this::convertMapToQuestionEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке данных из YAML", e);
        }
    }

    private QuestionEntity convertMapToQuestionEntity(Map<String, Object> questionMap) {
        String question = getStringValue(questionMap, "question");
        String answer = getStringValue(questionMap, "answer");
        return new QuestionEntity(question, answer);
    }

    private String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Отсутствует обязательное поле: " + key);
        }
        return (String) value;
    }

    public QuestionEntity generateDailyQuestion() {
        List<QuestionEntity> questions = generateData();
        int randomIndex = RANDOM.nextInt();
        return questions.get(randomIndex);
    }

}
