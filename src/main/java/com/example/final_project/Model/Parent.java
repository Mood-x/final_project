package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Parent {
    @Id
    private Integer id;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Child> children;

//    @OneToMany(mappedBy = "parent")
//    private Set<Like> likes;

    @OneToMany(mappedBy = "parent")
    private Set<Comment> comments;

//    @OneToMany(mappedBy = "parent")
//    private Set<Rating> ratings;

    @OneToMany(mappedBy = "parent")
    private Set<Complaint> complaints;

    @ManyToMany
    @JoinTable(
            name = "parent_liked_centers",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "center_id")
    )
    private Set<Center> likedCenters;  // List of liked centers

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
}
