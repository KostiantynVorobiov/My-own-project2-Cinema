package com.cinema.validator;

import com.cinema.model.dto.UserRequestDto;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, UserRequestDto> {

    @Override
    public boolean isValid(UserRequestDto userRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        String password = userRequestDto.getPassword();
        String repeatPassword = userRequestDto.getRepeatPassword();
        return password != null && Objects.equals(password, repeatPassword);
    }
}
