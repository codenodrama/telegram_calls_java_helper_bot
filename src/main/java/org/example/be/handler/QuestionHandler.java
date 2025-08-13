package org.example.be.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.UnsupportedEncodingException;

public class QuestionHandler {
    public static Integer sendQuestion(TelegramClient telegramClient, String questionString) {
        SendMessage message = null;
        Message response = null;
        questionString = "*" + questionString + "*";
        try {
            message = SendMessage
                    .builder()
                    .chatId("-4882662303")
                    .text(new String(questionString.getBytes() , "UTF-8")).parseMode("Markdown")
                    .build();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try {
            response = telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return response.getMessageId();
    }
}
