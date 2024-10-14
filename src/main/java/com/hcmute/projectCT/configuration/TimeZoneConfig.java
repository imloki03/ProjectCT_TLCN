package com.hcmute.projectCT.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import java.util.TimeZone;

public class TimeZoneConfig {
    @Value("${time_zone}")
    String zone;
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(zone));
    }
}
