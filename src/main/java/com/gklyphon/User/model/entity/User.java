package com.gklyphon.User.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users",
        uniqueConstraints =
        @UniqueConstraint(columnNames =
                {
                        "email", "username"
                }
        ))
@AllArgsConstructor
public class User {

    public User() {
        authorities = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String phoneNumber;
    private String profileImage;
    private Boolean status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    Set<Authority> authorities;

    @ManyToOne
    private Country country;

    private LocalDate statusChangeAt;
    private LocalDate createAt;
    private LocalDate updateAt;
    private LocalDate lastLogin;
}
