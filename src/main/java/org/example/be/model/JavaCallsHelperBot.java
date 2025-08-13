package org.example.be.model;

import org.example.be.entity.QuestionEntity;
import org.example.be.entity.UserEntity;
import org.example.be.handler.QuestionHandler;
import org.example.be.refactor.AstonFirstStepQA;
import org.example.be.service.QuizMembersService;
import org.example.be.service.QuizService;
import org.example.be.util.QuestionsLoader;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.*;

public class JavaCallsHelperBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    private static QuizMembers quizMembers;

    private static final List<QuestionsLoader.Question> QUESTIONS = QuestionsLoader.loadQuestions();

    public AstonFirstStepQA astonFirstStepQA;

    private QuestionEntity dailyQuestion;

    private Integer dailyQuestionMessageId;

    private Set<Long> reactedUserSet;

    public class MyTimeTask extends TimerTask
    {
        public void run()
        {
            dailyQuestionMessageId = QuestionHandler.sendQuestion(telegramClient, dailyQuestion.getQuestion());
        }
    }

    public void createTimer(int hours, int minutes, String question) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        Date today = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(new MyTimeTask(), today);
    }


    public JavaCallsHelperBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
        astonFirstStepQA = new AstonFirstStepQA();
        quizMembers = new QuizMembers();
        dailyQuestionMessageId = null;
        dailyQuestion = new QuizService().generateDailyQuestion();
        createTimer(14, 00, dailyQuestion.getQuestion());
        reactedUserSet = new HashSet<>();
    }

    @Override
    public void consume(Update update) {

        if(update.getMessageReaction() != null && Objects.equals(update.getMessageReaction().getMessageId(), dailyQuestionMessageId) && !reactedUserSet.contains(update.getMessageReaction().getUser().getId())) {
            astonFirstStepQA.sendAnswer(telegramClient, astonFirstStepQA.generateAnswer(dailyQuestion.getAnswer()), update.getMessageReaction().getUser().getId());
            reactedUserSet.add(update.getMessageReaction().getUser().getId());
        }

        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getChatId().equals(-4882662303l))
        {
            String message_text = update.getMessage().getText();
            Long chat_id = update.getMessage().getChatId();

            if(message_text.startsWith("/clear")) {
                quizMembers.clear(chat_id);
            } else if(message_text.startsWith("/online")) {
                User user = update.getMessage().getFrom();
                UserEntity userEntity = new UserEntity(user.getId(),
                        user.getFirstName(), user.getLastName(), chat_id);
                quizMembers.addMember(chat_id, userEntity);
            } else if(message_text.startsWith("/create_list")) {
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text(QuizMembersService.randomListString(QuizMembers.getUserList(chat_id)))
                        .build();
                try {
                    telegramClient.execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        }
    }
