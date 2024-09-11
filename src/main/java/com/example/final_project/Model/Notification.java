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
}
