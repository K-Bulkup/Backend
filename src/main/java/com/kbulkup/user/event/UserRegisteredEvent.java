package com.kbulkup.user.event;

import com.kbulkup.user.event.dto.UserRegisteredEventDataDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {

    private final UserRegisteredEventDataDTO userEventDataDTO;

    public UserRegisteredEvent(Object source, UserRegisteredEventDataDTO dto) {
        super(source);
        this.userEventDataDTO = dto;
    }
}
