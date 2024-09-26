
package com.gklyphon.User.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "authorities",
    uniqueConstraints = @UniqueConstraint(
            columnNames = {
                    "authority", "user_id"
            }
    ))
@AllArgsConstructor
@NoArgsConstructor

public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    @ManyToOne
    private User user;

    private LocalDate createAt;
    private LocalDate updateAt;

    public Authority(Long id, String authority, User user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
    }
}
