package br.com.anderson_silva.Banking_system.customAnnotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy ={TypeBigDecimalValidator.class})
public @interface TypeBigDecimal {
    String message() default "O campo amountDestiny apresenta formato inválido  use ex:[1.0],[1.000],[0,10],[1.100,00] e maior que 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "";
}
