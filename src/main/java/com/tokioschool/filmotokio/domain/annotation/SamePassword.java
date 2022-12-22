package com.tokioschool.filmotokio.domain.annotation;

import com.tokioschool.filmotokio.domain.validator.SamePasswordValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = SamePasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SamePassword {
  String message() default "Passwords do not match";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
}
