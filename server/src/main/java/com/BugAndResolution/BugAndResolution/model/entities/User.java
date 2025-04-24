package com.BugAndResolution.BugAndResolution.model.entities;


import com.BugAndResolution.BugAndResolution.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    private String name;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

    @Column(name="CreatedAt")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "developer")
    private List<Bug> bugs;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

}
