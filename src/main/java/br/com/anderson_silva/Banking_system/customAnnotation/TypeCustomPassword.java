package br.com.anderson_silva.Banking_system.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TypeCustomPassword implements ConstraintValidator<CustomPassword,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        try {
            return (s.length()>=8 && s.length()<=16);

        }catch (Exception e){
            return false;
        }



    }
}
