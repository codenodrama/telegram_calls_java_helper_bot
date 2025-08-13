package org.example.be.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CustomSendMessage extends SendMessage {

    protected CustomSendMessage(SendMessageBuilder<?, ?> b) {
        super(b);
    }
}
