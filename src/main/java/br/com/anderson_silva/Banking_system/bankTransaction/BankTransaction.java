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
                    System.out.println("authorized deposit!");
                    System.out.println("type user "+userOrigin.getType_user());
                    ///depositar
                    makeDeposit(userOrigin,userdestiny,balance);

                }else{
                    System.out.println(balance.compareTo(new BigDecimal("0.0"))!=0?"authorized deposit":"unauthorized deposit!");
                    System.out.println("type user client: "+!userOrigin.getType_user().equals("shopkeeper"));
                    return false;
                }



            }else{
                System.out.println(userOrigin==null?"Origin not found transfer canceled":"Origin Found");
                System.out.println(userdestiny==null?"Destiny not found transfer canceled":"Destiny Found");

                return false;
            }
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        return false;
    }

    private void makeDeposit(User userOrigin,User userDestiny,BigDecimal amount){

        System.out.println("Origin: "+userOrigin.getFull_name());
        System.out.println("Destiny: "+userDestiny.getFull_name());
        System.out.println("Amount:"+amount);
        BigDecimal newAmountDestiny=amount.add(userDestiny.getWallet());
        BigDecimal newAmountOrigin=userOrigin.getWallet().subtract(amount);
        int updateOrigin=myRepo.updateBalance(userOrigin.getCpf_cnpj(),newAmountOrigin);
        if(updateOrigin!=0){
            System.out.println("transfer completed: ");
            int updateDestiny=myRepo.updateBalance(userDestiny.getCpf_cnpj(),newAmountDestiny);
            if(updateDestiny!=0){
                System.out.println("deposit completed");
                ///enviar email de confirmação

            }else{
                System.out.println("operation failed!");
                myRepo.updateBalance(userOrigin.getCpf_cnpj(),newAmountOrigin.add(amount));
            }

        }else{
            System.out.println("operation failed!");
        }


    }
}
