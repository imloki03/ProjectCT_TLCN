package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.*;
import com.hcmute.projectCT.enums.MediaType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MediaContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String filename;
    private String size;
    private LocalDateTime uploadTime;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    private String link;

    @OneToOne
    @JoinColumn(name = "previous_version_id")
    private MediaContent previousVersion;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
