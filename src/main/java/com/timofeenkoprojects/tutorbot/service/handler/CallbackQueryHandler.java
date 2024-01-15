package com.timofeenkoprojects.tutorbot.service.handler;

import com.timofeenkoprojects.tutorbot.service.manager.auth.AuthManager;
import com.timofeenkoprojects.tutorbot.service.manager.feedback.FeedbackManager;
import com.timofeenkoprojects.tutorbot.service.manager.help.HelpManager;
import com.timofeenkoprojects.tutorbot.service.manager.profile.ProfileManager;
import com.timofeenkoprojects.tutorbot.service.manager.progress_control.ProgressControlManager;
import com.timofeenkoprojects.tutorbot.service.manager.search.SearchManager;
import com.timofeenkoprojects.tutorbot.service.manager.task.TaskManager;
import com.timofeenkoprojects.tutorbot.service.manager.timetable.TimetableManager;
import com.timofeenkoprojects.tutorbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.timofeenkoprojects.tutorbot.service.data.CallbackData.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallbackQueryHandler {
    final HelpManager helpManager;
    final FeedbackManager feedbackManager;
    final TimetableManager timetableManager;
    final TaskManager taskManager;
    final AuthManager authManager;
    final ProfileManager profileManager;
    final SearchManager searchManager;
    final ProgressControlManager progressControlManager;
    @Autowired
    public CallbackQueryHandler(HelpManager helpManager,
                                FeedbackManager feedbackManager,
                                TimetableManager timetableManager,
                                TaskManager taskManager,
                                AuthManager authManager,
                                ProfileManager profileManager,
                                SearchManager searchManager,
                                ProgressControlManager progressControlManager) {
        this.helpManager = helpManager;
        this.feedbackManager = feedbackManager;
        this.timetableManager = timetableManager;
        this.taskManager = taskManager;
        this.authManager = authManager;
        this.profileManager = profileManager;
        this.searchManager = searchManager;
        this.progressControlManager = progressControlManager;
    }

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String callbackData = callbackQuery.getData();
        String keyWord = callbackData.split("_")[0];
        switch (keyWord) {
            case TIMETABLE -> {
                return timetableManager.answerCallbackQuery(callbackQuery, bot);
            }
            case TASK -> {
                return taskManager.answerCallbackQuery(callbackQuery, bot);
            }
            case PROGRESS -> {
                return progressControlManager.answerCallbackQuery(callbackQuery, bot);
            }
            case AUTH -> {
                return authManager.answerCallbackQuery(callbackQuery, bot);
            }
            case PROFILE -> {
                return profileManager.answerCallbackQuery(callbackQuery, bot);
            }
            case SEARCH -> {
                return searchManager.answerCallbackQuery(callbackQuery, bot);
            }
        }
        switch (callbackData) {
            case FEEDBACK -> {
                return feedbackManager.answerCallbackQuery(callbackQuery, bot);
            }
            case HELP -> {
                return helpManager.answerCallbackQuery(callbackQuery, bot);
            }
        }
        return null;

    }
}
