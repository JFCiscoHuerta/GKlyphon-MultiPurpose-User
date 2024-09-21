package com.gklyphon.User.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "authorities",
    uniqueConstraints = @UniqueConstraint(
            columnNames = {
                    "authority", "user_id"
            }
    ))
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    @ManyToOne
    private User user;

    private LocalDate createAt;
    private LocalDate updateAt;
}
