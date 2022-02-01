package br.com.anderson_silva.Banking_system.dto.request;

import br.com.anderson_silva.Banking_system.customAnnotation.CpfOrCnpj;
import br.com.anderson_silva.Banking_system.customAnnotation.TypeBigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {
    @NotBlank(message = "o campo transactionPassword deve ficar entre 8 e 16 caracteres")
    @Size(min = 8,max = 16,message = "o campo transactionPassword deve ficar entre 8 e 16 caracteres")
    private String transactionPassword;
    @NotNull(message = "campo cpfCnpj inválido formato aceito ex:cpf[xxx.xxx.xxx-xx] ou cnpj[xxx.xxx.xxx-xx]")
    @CpfOrCnpj
    private String cpfCnpjDestiny;
    @TypeBigDecimal
    private String amountDestiny;
}
