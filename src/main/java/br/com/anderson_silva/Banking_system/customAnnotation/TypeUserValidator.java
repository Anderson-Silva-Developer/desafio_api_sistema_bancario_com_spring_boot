package br.com.anderson_silva.Banking_system.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;


public class TypeUserValidator implements ConstraintValidator<TypeUser,String> {
    List<String> listUsers=Arrays.asList("client","shopkeeper");
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return listUsers.contains(s);
    }
}
