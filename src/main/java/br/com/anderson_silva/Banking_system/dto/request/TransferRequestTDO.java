package br.com.anderson_silva.Banking_system.dto.request;

import br.com.anderson_silva.Banking_system.customAnnotation.CpfOrCnpj;
import br.com.anderson_silva.Banking_system.customAnnotation.TypeBigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestTDO {
    @NotBlank(message = "o campo transactionPassword deve ficar entre 8 e 16 caracteres")
    @Size(min = 8,max = 16,message = "o campo transactionPassword deve ficar entre 8 e 16 caracteres")
    private String transactionPassword;
    @CpfOrCnpj
    private String cpfCnpj;
    @TypeBigDecimal
    private String amountDestiny;
}
