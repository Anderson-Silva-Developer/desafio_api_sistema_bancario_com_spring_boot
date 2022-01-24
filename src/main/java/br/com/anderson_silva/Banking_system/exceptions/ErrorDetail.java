package br.com.anderson_silva.Banking_system.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetail {
    private int status;
    private String detail;

}
