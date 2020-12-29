package kr.co.momdeal.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ImageFilesValidator.class})
public @interface ValidImages {
    String message() default "Invalid image file type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
