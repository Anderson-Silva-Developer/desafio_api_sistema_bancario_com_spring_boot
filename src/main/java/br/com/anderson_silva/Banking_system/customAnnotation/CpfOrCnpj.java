package br.com.anderson_silva.Banking_system.customAnnotation;

import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.AND;
import static org.hibernate.validator.constraints.CompositionType.OR;

@Documented
@ConstraintComposition(OR)
@ReportAsSingleViolation
@CPF
@CNPJ
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Repeatable(CpfOrCnpj.List.class)
public @interface CpfOrCnpj {

    String message() default"campo cpfCnpj inválido formato aceito ex:cpf[xxx.xxx.xxx-xx] ou cnpj[xxx.xxx.xxx-xx]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        CpfOrCnpj[] value();

    }

}