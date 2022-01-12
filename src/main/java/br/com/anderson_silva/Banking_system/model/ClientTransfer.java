package br.com.anderson_silva.Banking_system.model;


import br.com.anderson_silva.Banking_system.customAnnotation.CpfOrCnpj;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientTransfer {
    @NotNull(message = "O campo password deve ser preenchido")
    @Size(min = 8,max = 16)
    private String password;
    @CpfOrCnpj
    private String cpf_cnpj_destiny;
    private String transfer_amount_destiny;
}
