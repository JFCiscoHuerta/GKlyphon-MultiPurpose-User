package com.gklyphon.User.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    public Country(Long id, String countryName, String code) {
        this.id = id;
        this.countryName = countryName;
        this.code = code;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String countryName;
    private String code;
    private LocalDate createAt;
    private LocalDate updateAt;
}
