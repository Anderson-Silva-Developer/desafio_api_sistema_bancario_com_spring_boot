package br.com.anderson_silva.Banking_system.bankTransaction;

import br.com.anderson_silva.Banking_system.model.ClientTransfer;
import br.com.anderson_silva.Banking_system.model.User;
import br.com.anderson_silva.Banking_system.notification.SendMail;
import br.com.anderson_silva.Banking_system.repository.UserRepository;
import br.com.anderson_silva.Banking_system.util.BeanUtil;
import br.com.anderson_silva.Banking_system.validator.ValidatorBankTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankTransaction {
    private UserRepository repository = BeanUtil.getBean(UserRepository.class);
    private ValidatorBankTransaction validatorBankTransaction=new ValidatorBankTransaction();

    public boolean transfer(ClientTransfer clientTransfer, Authentication auth ){
        try {
            Optional<User> optUsuario=this.repository.findByEmail(auth.getName());
            User userOrigin=optUsuario.get();
            User userdestiny=this.repository.findUser(clientTransfer.getCpf_cnpj_destiny());

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

    private void makeDeposit(User userOrigin,User userDestiny,BigDecimal amount) throws IOException {

        System.out.println("Origin: "+userOrigin.getFull_name());
        System.out.println("Destiny: "+userDestiny.getFull_name());
        System.out.println("Amount:"+amount);
        BigDecimal newAmountDestiny=amount.add(userDestiny.getWallet());
        BigDecimal newAmountOrigin=userOrigin.getWallet().subtract(amount);
        int updateOrigin= repository.updateBalance(userOrigin.getCpf_cnpj(),newAmountOrigin);
        if(updateOrigin!=0){
            System.out.println("transfer completed: ");
            int updateDestiny= repository.updateBalance(userDestiny.getCpf_cnpj(),newAmountDestiny);
            if(updateDestiny!=0){
                System.out.println("deposit completed");
                ///enviar email de confirmação
                DecimalFormat df = new DecimalFormat("###,##0.00");


                new SendMail().send(userOrigin.getFull_name(),df.format(amount),userDestiny.getEmail());

            }else{
                System.out.println("operation failed!");
                repository.updateBalance(userOrigin.getCpf_cnpj(),newAmountOrigin.add(amount));
            }

        }else{
            System.out.println("operation failed!");
        }


    }
}
