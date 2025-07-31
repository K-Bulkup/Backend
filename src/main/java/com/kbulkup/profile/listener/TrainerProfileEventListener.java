package com.kbulkup.profile.listener;

import com.kbulkup.profile.service.TrainerProfileService;
import com.kbulkup.user.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerProfileEventListener {

    private final TrainerProfileService trainerProfileService;

    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        trainerProfileService.createInitialProfile(event.getUser().getUserId());
    }
}
