package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnoreProperties("messageList")
    private User sender;

    private String content;
    private LocalDateTime sentTime;
    private boolean isPinned;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("messageList")
    private Project project;

    @OneToOne(mappedBy = "message", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("message")
    private MediaContent media;
}
