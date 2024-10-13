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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private String content;
    private LocalDateTime sentTime;
    private boolean isPinned;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(mappedBy = "message", cascade = CascadeType.REMOVE)
    private MediaContent media;
}