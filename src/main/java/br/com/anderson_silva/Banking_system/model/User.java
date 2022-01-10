package br.com.anderson_silva.Banking_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private  Long id;
    private BigDecimal wallet=new BigDecimal("1000");
    private  String full_name;
    private  String cpf_cnpj;
    private  String email;
    private  String password;
    private  String type_user="";

}
