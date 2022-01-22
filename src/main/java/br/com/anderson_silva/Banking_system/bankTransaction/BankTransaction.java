//package br.com.anderson_silva.Banking_system.bankTransaction;
//
//import br.com.anderson_silva.Banking_system.entities.User;
//import br.com.anderson_silva.Banking_system.notification.SendMail;
//import br.com.anderson_silva.Banking_system.repositories.UserRepository;
//import br.com.anderson_silva.Banking_system.util.BeanUtil;
//import br.com.anderson_silva.Banking_system.validator.ValidatorBankTransaction;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.util.Optional;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class BankTransaction {
//    private UserRepository repository = BeanUtil.getBean(UserRepository.class);
//    private ValidatorBankTransaction validatorBankTransaction=new ValidatorBankTransaction();
//
//
//    public boolean transfer(ClientTransfer clientTransfer, Authentication auth,final PasswordEncoder encoder){
//        try {
//            Optional<User> optUsuario=this.repository.findByEmail(auth.getName());
//            User userOrigin=optUsuario.get();
//            User userdestiny=this.repository.findUser(clientTransfer.getCpf_cnpj());
//
//            boolean isValidTransaction=encoder.matches(clientTransfer.getTransaction_password(),userOrigin.getTransaction_password());
//            System.out.println(isValidTransaction);
//            BigDecimal balance = this.validatorBankTransaction.validateBalance(userOrigin, clientTransfer);
//
//            if(userOrigin!=null && userdestiny!=null && !userOrigin.getType_user().equals("shopkeeper")&&
//                    isValidTransaction && (balance.compareTo(new BigDecimal("0.0"))!=0) ){
//                   ///deposit
//                   return makeDeposit(userOrigin,userdestiny,balance);
//            }
//            return false;
//
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//
//        }
//        return false;
//    }
//
//    private boolean makeDeposit(User userOrigin,User userDestiny,BigDecimal amount) throws IOException {
//        try {
//            BigDecimal newAmountDestiny=amount.add(userDestiny.getWallet());
//            BigDecimal newAmountOrigin=userOrigin.getWallet().subtract(amount);
//
//            int updateOrigin= repository.updateBalance(userOrigin.getCpf_cnpj(),newAmountOrigin);
//            if(updateOrigin!=0){
//                int updateDestiny= repository.updateBalance(userDestiny.getCpf_cnpj(),newAmountDestiny);
//                if(updateDestiny!=0){
//                    ///enviar email de confirmação
//                    DecimalFormat df = new DecimalFormat("###,##0.00");
//                    new SendMail().send(userOrigin.getFull_name(),df.format(amount),userDestiny.getEmail());
//                    return true;
//                }else{
//                    repository.updateBalance(userOrigin.getCpf_cnpj(),newAmountOrigin.add(amount));
//                }
//            }
//            return false;
//
//        }catch (Exception e){
//            throw e;
//
//        }
//
//    }
//}
