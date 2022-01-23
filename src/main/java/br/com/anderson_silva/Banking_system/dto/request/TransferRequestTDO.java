package br.com.anderson_silva.Banking_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestTDO {
    private String transactionPassword;
    private String cpfCnpj;
    private String amountDestiny;
}
