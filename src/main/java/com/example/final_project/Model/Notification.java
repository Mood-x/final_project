package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


    @NotEmpty(message = "Message should be not empty")
    @Column(columnDefinition = "text not null")
    private String message;

    private boolean isRead = false;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    private User user;

    public enum NotificationType{
        ADMIN_TO_PARENT,
        ADMIN_TO_CENTER,
        CENTER_TO_PARENT,
        CENTER_TO_ADMIN,
        PARENT_TO_ADMIN,
        PARENT_TO_CENTER,
        REQUEST_PARTICIPATION
    }
}
