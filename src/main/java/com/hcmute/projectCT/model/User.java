package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String email;
    private String password;
    private String gender;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private UserStatus status;

    private String avatarURL;

    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tagList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Notification> notificationList;

    @OneToMany(mappedBy = "owner")
    private List<Project> projectList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Collaborator> collaboratorList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
