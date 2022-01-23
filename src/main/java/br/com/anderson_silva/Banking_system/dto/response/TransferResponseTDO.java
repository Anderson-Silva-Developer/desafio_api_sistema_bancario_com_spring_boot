package br.com.anderson_silva.Banking_system.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseTDO {
    private  String status;
    private  String fullName;
    private BigDecimal AmountDestiny;
}
