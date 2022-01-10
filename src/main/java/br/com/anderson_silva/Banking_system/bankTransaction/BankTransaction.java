package br.com.anderson_silva.Banking_system.bankTransaction;

import br.com.anderson_silva.Banking_system.model.ClientTransfer;
import br.com.anderson_silva.Banking_system.model.User;
import br.com.anderson_silva.Banking_system.repository.UserRepository;
import br.com.anderson_silva.Banking_system.util.BeanUtil;
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
    private UserRepository myRepo = BeanUtil.getBean(UserRepository.class);
    private ValidatorUser validatorUser=new ValidatorUser();
    private ValidatorBankTransaction validatorBankTransaction=new ValidatorBankTransaction();

    public boolean transfer(ClientTransfer clientTransfer){
        try {
            User userOrigin= this.validatorUser.validate_user(clientTransfer.getCpf_cnpj_origin(),clientTransfer.getPassword());
            User userdestiny= this.validatorUser.validate_user(clientTransfer.getCpf_cnpj_destiny(),"");
            if(userOrigin!=null && userdestiny!=null && !userOrigin.getType_user().equals("shopkeeper")){

                BigDecimal balance = this.validatorBankTransaction.validate_balance(userOrigin, clientTransfer);

                if(balance.compareTo(new BigDecimal("0.0"))!=0){
                    System.out.println("transferir amount"+clientTransfer.getTransfer_amount_destiny());
                    System.out.println("Origin :"+userOrigin.getFull_name());
                    System.out.println("Origin wallet:"+userOrigin.getWallet());
                    System.out.println("Destiny :"+userdestiny.getFull_name());
                    System.out.println("deposito altorizado!");
                    System.out.println("type user "+userOrigin.getType_user());
                    ///depositar
                    makeDeposit(userOrigin,userdestiny,balance);

                }else{
                    System.out.println(balance.compareTo(new BigDecimal("0.0"))!=0?"deposito altorizado":"deposito não altorizado!");
                    System.out.println("type user client: "+!userOrigin.getType_user().equals("shopkeeper"));
                }



            }else{
                System.out.println(userOrigin==null?"Origin não encontrado transferencia cancelada":"Origin Encontrado");
                System.out.println(userdestiny==null?"Destiny não encontrado transferencia cancelada":"Destiny Encontrado");
                System.out.println("-------------------------------------------------------");
            }
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        return false;
    }

    private void makeDeposit(User userOrigin,User userDestiny,BigDecimal amount){
        System.out.println("DE: "+userOrigin.getFull_name());
        System.out.println("PARA: "+userDestiny.getFull_name());
        System.out.println("AMOUNT:"+amount);
        BigDecimal newAmountDestiny=amount.add(userDestiny.getWallet());
        myRepo.updateBalance(userDestiny.getCpf_cnpj(),newAmountDestiny);
        System.out.println("deposito concluido: ");
        BigDecimal newAmountOrigin=userOrigin.getWallet().subtract(amount);
        myRepo.updateBalance(userOrigin.getCpf_cnpj(),newAmountOrigin);
        System.out.println("transferencia concluido: ");

    }
}
