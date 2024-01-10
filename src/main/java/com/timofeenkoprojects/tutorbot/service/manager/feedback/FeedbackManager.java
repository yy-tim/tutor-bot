package com.timofeenkoprojects.tutorbot.service.manager.feedback;

import com.timofeenkoprojects.tutorbot.service.factory.AnswerMethodFactory;
import com.timofeenkoprojects.tutorbot.service.factory.KeyboardFactory;
import com.timofeenkoprojects.tutorbot.service.manager.AbstractManager;
import com.timofeenkoprojects.tutorbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackManager extends AbstractManager {
    final AnswerMethodFactory methodFactory;
    final KeyboardFactory keyboardFactory;

    public FeedbackManager(AnswerMethodFactory methodFactory, KeyboardFactory keyboardFactory) {
        this.methodFactory = methodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return methodFactory.getSendMessage(
                message.getChatId(),
                """
                        Ссылки для обратной связи
                        https://t.me/+WhsUc28brR0xM2E6                        
                        """,
                null
        );

    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return methodFactory.getEditMessageText(callbackQuery,
                """
                        Ссылки для обратной связи
                        https://t.me/+WhsUc28brR0xM2E6                        \s
                        """,
                null
        );
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }
}
