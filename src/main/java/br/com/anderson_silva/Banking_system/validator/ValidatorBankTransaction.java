//package br.com.anderson_silva.Banking_system.validator;
//
//
//import br.com.anderson_silva.Banking_system.entities.User;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.math.BigDecimal;
//
//@NoArgsConstructor
//@AllArgsConstructor
//public class ValidatorBankTransaction {
//    private PasswordEncoder encoder;
//
//
//    public BigDecimal validateBalance(User user, ClientTransfer clientTransfer){
//
//        BigDecimal amountBigDecimal = new BigDecimal(clientTransfer.getTransfer_amount_destiny().replaceAll("\\.", "").replace(",","."));
//        boolean result = (user.getWallet().compareTo(new BigDecimal(amountBigDecimal.toString())) >= 0);
//        return  result?amountBigDecimal:new BigDecimal("0.0");
//    }
//    public  boolean validatePassWordTransaction(String password,User user){
//        try {
//            return this.encoder.matches(password,user.getPassword());
//        }catch (Exception e){
//            throw e;
//        }
//
//    }
//
//
//
//}
