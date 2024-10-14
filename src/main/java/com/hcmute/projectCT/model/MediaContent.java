package com.hcmute.projectCT.model;

import com.hcmute.projectCT.enums.MediaType;
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
public class MediaContent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private String filename;
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
    private Message message;

    @ManyToOne
    private Task task;
}
