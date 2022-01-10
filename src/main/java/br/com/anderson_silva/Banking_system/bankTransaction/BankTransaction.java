package br.com.anderson_silva.Banking_system.bankTransaction;

import br.com.anderson_silva.Banking_system.model.ClientTransfer;
import br.com.anderson_silva.Banking_system.model.User;
import br.com.anderson_silva.Banking_system.validator.ValidatorBankTransaction;
import br.com.anderson_silva.Banking_system.validator.ValidatorUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankTransaction {
    private ValidatorUser validatorUser=new ValidatorUser();
    private ValidatorBankTransaction validatorBankTransaction=new ValidatorBankTransaction();
    public boolean transfer(ClientTransfer clientTransfer){
        try {
            User userOrigin= this.validatorUser.validate_user(clientTransfer.getCpf_cnpj_origin(),clientTransfer.getPassword());
            User userdestiny= this.validatorUser.validate_user(clientTransfer.getCpf_cnpj_destiny(),"");
            if(userOrigin!=null && userdestiny!=null){
                BigDecimal balance = this.validatorBankTransaction.validate_balance(userOrigin, clientTransfer);

                System.out.println("transferir "+clientTransfer.getTransfer_amount_destiny());
                System.out.println("Origin :"+userOrigin.getFull_name());
                System.out.println("Origin wallet:"+userOrigin.getWallet());
                System.out.println("Destiny :"+userdestiny.getFull_name());
                System.out.println(!balance.toString().equals("0")?"deposito ok":"deposito não viavel");

            }else{
                System.out.println(userOrigin==null?"Origin não encontrado":"Origin Encontrado");
                System.out.println(userdestiny==null?"Destiny não encontrado":"Destiny Encontrado");
                System.out.println("-------------------------------------------------------");
            }
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        return false;
    }
    public void toReceive(){

    }
}
