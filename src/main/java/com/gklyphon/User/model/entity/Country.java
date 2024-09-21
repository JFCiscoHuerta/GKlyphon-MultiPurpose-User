package com.gklyphon.User.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "countries",
        uniqueConstraints =
        @UniqueConstraint(columnNames =
                {
                        "country_name", "code"
                }
        ))
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String countryName;
    private String code;
    private LocalDate createAt;
    private LocalDate updateAt;
}
