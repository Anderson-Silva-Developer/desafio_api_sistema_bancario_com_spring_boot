package br.com.anderson_silva.Banking_system.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorDetails extends ErrorDetail{
    private String field;
    private String fieldMessage;

}
