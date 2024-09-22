package com.gklyphon.User.model.dto;

import com.gklyphon.User.model.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String phoneNumber;
    private String profileImage;
    private Country country;
}
