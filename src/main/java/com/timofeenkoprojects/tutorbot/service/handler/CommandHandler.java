package com.timofeenkoprojects.tutorbot.service.handler;

import com.timofeenkoprojects.tutorbot.service.manager.feedback.FeedbackManager;
import com.timofeenkoprojects.tutorbot.service.manager.help.HelpManager;
import com.timofeenkoprojects.tutorbot.service.manager.profile.ProfileManager;
import com.timofeenkoprojects.tutorbot.service.manager.progress_control.ProgressControlManager;
import com.timofeenkoprojects.tutorbot.service.manager.search.SearchManager;
import com.timofeenkoprojects.tutorbot.service.manager.start.StartManager;
import com.timofeenkoprojects.tutorbot.service.manager.task.TaskManager;
import com.timofeenkoprojects.tutorbot.service.manager.timetable.TimetableManager;
import com.timofeenkoprojects.tutorbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


import static com.timofeenkoprojects.tutorbot.service.data.Command.*;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandHandler {

    final FeedbackManager feedbackManager;
    final HelpManager helpManager;
    final StartManager startManager;
    final ProfileManager profileManager;
    final TimetableManager timetableManager;
    final TaskManager taskManager;
    final SearchManager searchManager;
    final ProgressControlManager progressControlManager;
    @Autowired
    public CommandHandler(FeedbackManager feedbackManager,
                          HelpManager helpManager,
                          StartManager startManager,
                          ProfileManager profileManager,
                          TimetableManager timetableManager,
                          TaskManager taskManager, SearchManager searchManager,
                          ProgressControlManager progressControlManager) {
        this.feedbackManager = feedbackManager;
        this.helpManager = helpManager;
        this.startManager = startManager;
        this.profileManager = profileManager;
        this.timetableManager = timetableManager;
        this.taskManager = taskManager;
        this.searchManager = searchManager;
        this.progressControlManager = progressControlManager;
    }

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();
        switch (command) {
            case START -> {
                return startManager.answerCommand(message, bot);
            }
            case FEEDBACK_COMMAND -> {
                return feedbackManager.answerCommand(message, bot);
            }
            case HELP_COMMAND -> {
                return helpManager.answerCommand(message, bot);
            }
            case TIMETABLE -> {
                return timetableManager.answerCommand(message, bot);
            }
            case TASK -> {
                return taskManager.answerCommand(message, bot);
            }
            case PROGRESS -> {
                return progressControlManager.answerCommand(message, bot);
            }
            case PROFILE -> {
                return profileManager.answerCommand(message, bot);
            }
            case SEARCH -> {
                return searchManager.answerCommand(message, bot);
            }
            default -> {
                return defaultAnswer(message);
            }
        }

    }

    private BotApiMethod<?> defaultAnswer(Message message) {
        return SendMessage.builder()
                .text("Неподдерживаемая команда!")
                .chatId(message.getChatId())
                .build();
    }

}
