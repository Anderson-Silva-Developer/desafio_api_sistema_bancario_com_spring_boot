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
            BigDecimal bigDecimal=new BigDecimal(amountBigDecimal.toString());
            if(bigDecimal.compareTo(new BigDecimal("0.0"))>0)
                return true;
            return false;
        }catch (Exception e){
            return false;
        }

    }
}
