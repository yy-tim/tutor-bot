package com.timofeenkoprojects.tutorbot.service.handler;

import com.timofeenkoprojects.tutorbot.repository.UserRepo;
import com.timofeenkoprojects.tutorbot.service.manager.search.SearchManager;
import com.timofeenkoprojects.tutorbot.service.manager.timetable.TimetableManager;
import com.timofeenkoprojects.tutorbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageHandler {
    final SearchManager searchManager;
    final UserRepo userRepo;
    final TimetableManager timetableManager;

    @Autowired
    public MessageHandler(SearchManager searchManager,
                          UserRepo userRepo, TimetableManager timetableManager) {
        this.searchManager = searchManager;
        this.userRepo = userRepo;
        this.timetableManager = timetableManager;
    }
    public BotApiMethod<?> answer(Message message, Bot bot) {
        var user = userRepo.findUserByChatId(message.getChatId());
        switch (user.getAction()) {
            case SENDING_TOKEN -> {
                return searchManager.answerMessage(message, bot);
            }
            case SENDING_DESCRIPTION, SENDING_TITLE -> {
                return timetableManager.answerMessage(message, bot);
            }
        }
        return null;
    }
}
