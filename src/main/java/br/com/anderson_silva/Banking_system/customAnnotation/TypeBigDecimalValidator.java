package br.com.anderson_silva.Banking_system.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class TypeBigDecimalValidator implements ConstraintValidator<TypeBigDecimal,String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            new BigDecimal(s);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
