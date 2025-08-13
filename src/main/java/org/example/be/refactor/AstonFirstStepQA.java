package org.example.be.refactor;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class AstonFirstStepQA {
    public static Map<String, String> qaMap;

    private List<String> keysAsArray;

    private String question;

    public String generateQuestion() {
        keysAsArray = new ArrayList<String>(qaMap.keySet());
        Random r = new Random();
        String question = keysAsArray.get(r.nextInt(keysAsArray.size()));
        return question;
    }

    public String generateAnswer(String question) {
        return qaMap.get(question);
    }

    public Integer sendQuestion(TelegramClient telegramClient, String questionString) {
        SendMessage message = null;
        Message response = null;
        questionString = "*" + questionString + "*";
            try {
                message = SendMessage // Create a message object
                        .builder()
                        .chatId("-4882662303")
                        .text(new String(questionString.getBytes() , "UTF-8")).parseMode("Markdown")
                        .build();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            try {
                response = telegramClient.execute(message);// Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        return response.getMessageId();
        }

    public void sendAnswer(TelegramClient telegramClient, String answerString, Long id) {
        SendMessage message = null;
            try {
                message = SendMessage // Create a message object
                        .builder()
                        .chatId(id)
                        .text(new String(answerString.getBytes() , "UTF-8")).parseMode("Markdown")
                        .build();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            try {
                telegramClient.execute(message);// Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
}
