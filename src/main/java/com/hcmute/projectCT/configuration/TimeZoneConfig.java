package com.hcmute.projectCT.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {
    @Value("${time_zone}")
    String zone;
    @PostConstruct
    public void init() {
        System.out.println("TimeZone: "+zone);
        TimeZone.setDefault(TimeZone.getTimeZone(zone));
        System.out.println("Current Time: " + LocalDateTime.now());
    }
}
