package com.cinema.model.dto;

import com.cinema.validator.ValidEmail;
import com.cinema.validator.ValidPassword;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@ValidPassword
public class UserRequestDto {
    @NotNull(message = "Email can't be empty")
    @ValidEmail
    private String email;
    @NotNull
    @Size(min = 5, message = "Should be min 5 characters")
    private String password;
    @NotNull
    private String repeatPassword;
}
