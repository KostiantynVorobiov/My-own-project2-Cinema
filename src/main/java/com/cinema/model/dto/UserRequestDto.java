package com.cinema.model.dto;

import com.cinema.validator.ValidEmail;
import com.cinema.validator.ValidPassword;
import lombok.Data;

@Data
@ValidPassword
public class UserRequestDto {
    @ValidEmail
    private String email;
    private String password;
    private String repeatPassword;
}
