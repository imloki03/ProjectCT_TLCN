package com.hcmute.projectCT.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String content;
    private LocalDateTime time;
    private String reference;
    private boolean isReaded;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}