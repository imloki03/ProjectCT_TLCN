package com.hcmute.projectCT.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageUtil {
    private final MessageSource messageSource;

    public String getMessage(String messageKey, Object... params) {
        return messageSource.getMessage(messageKey, params, WebUtil.getCurrentRequest().getLocale());
    }
}
