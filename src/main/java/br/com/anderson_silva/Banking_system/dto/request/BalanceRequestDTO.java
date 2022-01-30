package br.com.anderson_silva.Banking_system.dto.request;

import br.com.anderson_silva.Banking_system.customAnnotation.CustomPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequestDTO {
    @CustomPassword(message = "o campo transactionPassword deve ficar entre 8 e 16 caracteres ")
    private String transactionPassword;
}
