package br.com.anderson_silva.Banking_system.validator;


import br.com.anderson_silva.Banking_system.model.ClientTransfer;
import br.com.anderson_silva.Banking_system.model.User;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class ValidatorBankTransaction {

    public BigDecimal validate_balance(User user, ClientTransfer clientTransfer){

        BigDecimal amountBigDecimal = new BigDecimal(clientTransfer.getTransfer_amount_destiny().replaceAll("\\.", "").replace(",","."));
        boolean result = (user.getWallet().compareTo(new BigDecimal(amountBigDecimal.toString())) >= 0);
        return  result?amountBigDecimal:new BigDecimal("0.0");
    }


}
