package br.com.anderson_silva.Banking_system.customAnnotation;

import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.OR;

@Documented
@ConstraintComposition(OR)
@CPF(message = "campo cpfCnpj inválido formato aceito ex:cpf[xxx.xxx.xxx-xx] ou ,cnpj[xxx.xxx.xxx-xx]")
@CNPJ(message = "")
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface CpfOrCnpj {

    String message() default"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}