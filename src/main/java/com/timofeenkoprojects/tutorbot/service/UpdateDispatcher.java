package com.timofeenkoprojects.tutorbot.service;

import com.timofeenkoprojects.tutorbot.entity.user.User;
import com.timofeenkoprojects.tutorbot.repository.UserRepo;
import com.timofeenkoprojects.tutorbot.service.handler.CallbackQueryHandler;
import com.timofeenkoprojects.tutorbot.service.handler.CommandHandler;
import com.timofeenkoprojects.tutorbot.service.handler.MessageHander;
import com.timofeenkoprojects.tutorbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UpdateDispatcher {
    final MessageHander messageHandler;
    final CommandHandler commandHandler;
    final CallbackQueryHandler callbackQueryHandler;
    final UserRepo userRepo;

    @Autowired
    public UpdateDispatcher(MessageHander messageHander,
                            CommandHandler commandHandler,
                            CallbackQueryHandler callbackQueryHandler, UserRepo userRepo) {
        this.messageHandler = messageHander;
        this.commandHandler = commandHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.userRepo = userRepo;
    }

    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                userRepo.save(User.builder()
                        .chatId(message.getChatId())
                        .build());
                if (message.getText().charAt(0) == '/') {
                    return commandHandler.answer(message, bot);
                }
            }
            return messageHandler.answer(message, bot);
        }
        log.info("Unsopported update: " + update);
        return null;
    }
}
