package com.timofeenkoprojects.tutorbot.service.manager.profile;

import com.timofeenkoprojects.tutorbot.repository.UserRepo;
import com.timofeenkoprojects.tutorbot.service.factory.AnswerMethodFactory;
import com.timofeenkoprojects.tutorbot.service.factory.KeyboardFactory;
import com.timofeenkoprojects.tutorbot.service.manager.AbstractManager;
import com.timofeenkoprojects.tutorbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import static com.timofeenkoprojects.tutorbot.service.data.CallbackData.PROFILE_REFRESH_TOKEN;
@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileManager extends AbstractManager {
    final AnswerMethodFactory methodFactory;
    final KeyboardFactory keyboardFactory;
    final UserRepo userRepo;

    @Autowired
    public ProfileManager(AnswerMethodFactory methodFactory,
                          KeyboardFactory keyboardFactory,
                          UserRepo userRepo) {
        this.methodFactory = methodFactory;
        this.keyboardFactory = keyboardFactory;
        this.userRepo = userRepo;
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return showProfile(message);
    }
    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }
    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery,
                                               Bot bot) {
        switch (callbackQuery.getData()) {
            case PROFILE_REFRESH_TOKEN -> {
                return refreshToken(callbackQuery);
            }
        }
        return null;
    }

    private BotApiMethod<?> showProfile(Message message) {
        Long chatId = message.getChatId();
        return methodFactory.getSendMessage(
                chatId,
                getProfileText(chatId),
                keyboardFactory.getInlineKeyboard(
                        List.of("Обновить токен"),
                        List.of(1),
                        List.of(PROFILE_REFRESH_TOKEN)
                )
        );
    }

    private BotApiMethod<?> showProfile(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        return methodFactory.getEditMessageText(
                callbackQuery,
                getProfileText(chatId),
                keyboardFactory.getInlineKeyboard(
                        List.of("Обновить токен"),
                        List.of(1),
                        List.of(PROFILE_REFRESH_TOKEN)
                )
        );
    }

    private String getProfileText(Long chatId) {
        StringBuilder text = new StringBuilder("\uD83D\uDC64 Профиль\n");
        var user = userRepo.findUserByChatId(chatId);
        var details = user.getDetails();
        if (details.getUsername() == null) {
            text.append("▪\uFE0FИмя пользователя - ").append(details.getUsername());
        } else {
            text.append("▪\uFE0FИмя пользователя - ").append(details.getFirstName());
        }
        text.append("\n▪\uFE0FРоль -").append(user.getRole().name());
        text.append("\n▪\uFE0FВаш уникальный токен - \n").append(user.getToken());
        text.append("\n\n*⚠️ - токен необходим для того, чтобы ученик или преподаватель могли установиться между собой связь");
        return text.toString();
    }
    private BotApiMethod<?> refreshToken(CallbackQuery callbackQuery) {
        var user = userRepo.findUserByChatId(callbackQuery.getMessage().getChatId());
        user.refreshToken();
        userRepo.save(user);
        return showProfile(callbackQuery);
    }
}
