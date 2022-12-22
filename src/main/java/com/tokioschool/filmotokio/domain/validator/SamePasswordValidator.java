package com.tokioschool.filmotokio.domain.validator;

import com.tokioschool.filmotokio.domain.annotation.SamePassword;
import com.tokioschool.filmotokio.domain.dto.PasswordDTO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class SamePasswordValidator implements ConstraintValidator<SamePassword, PasswordDTO> {

  @Override
  public boolean isValid(PasswordDTO passwordDTO,
      ConstraintValidatorContext constraintValidatorContext) {

    return StringUtils.equals(passwordDTO.getPassword(), passwordDTO.getConfirmPassword());
  }
}
