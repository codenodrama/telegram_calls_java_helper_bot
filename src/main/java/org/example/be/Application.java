package org.example.be;

import org.example.be.model.JavaCallsHelperBot;
import org.example.be.util.CustomGetUpdatesGenerator;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.TelegramUrl;


public class Application {
    public static void main(String[] args) {
        String botToken = "TOKEN";
        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(botToken, () -> TelegramUrl.DEFAULT_URL, new CustomGetUpdatesGenerator(), new JavaCallsHelperBot(botToken));
            System.out.println("JavaCallsHelperBot successfully started!");
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}