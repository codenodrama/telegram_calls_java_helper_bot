package org.example.be.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.longpolling.util.DefaultGetUpdatesGenerator;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


@AllArgsConstructor
@NoArgsConstructor
public class CustomGetUpdatesGenerator implements Function<Integer, GetUpdates> {

    private static final int GET_UPDATES_LIMIT = 100;
    private static final int GET_UPDATES_TIMEOUT = 50;

    private List<String> allowedUpdates = Arrays.asList("message_reaction", "message_reaction_count", "message",
            "update_id", "chat_member", "channel_post");

    @Override
    public GetUpdates apply(Integer lastReceivedUpdate) {
        return GetUpdates
                .builder()
                .limit(GET_UPDATES_LIMIT)
                .timeout(GET_UPDATES_TIMEOUT)
                .offset(lastReceivedUpdate + 1)
                .allowedUpdates(allowedUpdates)
                .build();
    }
}
