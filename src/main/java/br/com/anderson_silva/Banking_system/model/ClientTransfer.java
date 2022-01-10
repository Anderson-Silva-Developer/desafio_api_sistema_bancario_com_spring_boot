package br.com.anderson_silva.Banking_system.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientTransfer {
    private String email;
    private String password;
    private String cpf_cnpj_origin;
    private String cpf_cnpj_destiny;
    private String transfer_amount_destiny;
}
