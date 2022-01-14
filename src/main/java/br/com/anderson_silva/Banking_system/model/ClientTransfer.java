package br.com.anderson_silva.Banking_system.model;


import br.com.anderson_silva.Banking_system.customAnnotation.CpfOrCnpj;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientTransfer {

    @NotNull(message = "campo transaction_password inválido  não pode ser menor que 8 caracteres ou vazio")
    @Size( min = 8,message = "campo transaction_password inválido  não pode ser menor que 8 caracteres ou vazio")
    private String transaction_password;
    @NotNull
    @CpfOrCnpj
    private String cpf_cnpj;

    @NotEmpty(message = "campo transfer_amount_destiny não pode ser vazio")
    private String transfer_amount_destiny;
}
