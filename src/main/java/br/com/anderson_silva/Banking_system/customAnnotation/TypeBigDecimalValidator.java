package br.com.anderson_silva.Banking_system.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TypeBigDecimalValidator implements ConstraintValidator<TypeBigDecimal,String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {

            BigDecimal amountBigDecimal = new BigDecimal(s.replaceAll("\\.", "").replace(",","."));
            new BigDecimal(amountBigDecimal.toString());
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
