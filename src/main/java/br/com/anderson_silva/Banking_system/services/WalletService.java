package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.TransferRequestTDO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseTDO;
import br.com.anderson_silva.Banking_system.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
public class WalletService {
    private final UserService  userService;
    private final EncoderService encoderService;
    private final NotificationService notificationService;

    public WalletService(UserService userService, EncoderService encoderService, NotificationService notificationService) {
        this.userService = userService;
        this.encoderService = encoderService;

        this.notificationService = notificationService;
    }

    public TransferResponseTDO transfer(TransferRequestTDO transferReqTDO) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.isAuthenticated()){
            User userOrigin=this.userService.findByEmail(auth.getName());
            User userDestiny=this.userService.findByCpfCnpj(transferReqTDO.getCpfCnpj());
            boolean isPassword=this.encoderService.checkPassword(transferReqTDO,userOrigin);
            BigDecimal balance=this.checkBalance(userOrigin,transferReqTDO);

            if(userOrigin!=null && userDestiny!=null && isPassword && (balance.compareTo(new BigDecimal("0.0"))!=0)
                    && !userOrigin.getTypeUser().equals("shopkeeper")){

                boolean deposit=Deposit(userOrigin,userDestiny,balance);
                TransferResponseTDO transferResponseTDO = new TransferResponseTDO();
                if(deposit) {

                    transferResponseTDO.setStatus("Success");
                    transferResponseTDO.setAmountDestiny(balance);
                    transferResponseTDO.setFullName(userDestiny.getFullName());
                    DecimalFormat df = new DecimalFormat("###,##0.00");
                    int code =this.notificationService.sendMail(userOrigin.getFullName(),df.format(balance),userDestiny.getEmail());
                    return  transferResponseTDO;

                }else{
                    transferResponseTDO.setStatus("Failure");
                    transferResponseTDO.setAmountDestiny(balance);
                    transferResponseTDO.setFullName(userDestiny.getFullName());
                    return  transferResponseTDO;

                }

            }else{
                TransferResponseTDO transferResponseTDO = new TransferResponseTDO();
                transferResponseTDO.setStatus("Failure");
                transferResponseTDO.setAmountDestiny(balance);
                transferResponseTDO.setFullName(userDestiny.getFullName());
                return  transferResponseTDO;
            }

        }
        return null;

    }

    public BigDecimal checkBalance(User user, TransferRequestTDO transferRequestTDO){
//        BigDecimal amountBigDecimal = new BigDecimal(transferRequestTDO.getAmountDestiny().replaceAll("\\.", "").replace(",","."));
        BigDecimal amountBigDecimal = new BigDecimal(transferRequestTDO.getAmountDestiny());
        boolean result = (user.getWallet().getBalance().compareTo(new BigDecimal(amountBigDecimal.toString())) >= 0);
        return  result?amountBigDecimal:new BigDecimal("0.0");
    }

    public boolean Deposit(User userOrigin,User userDestiny,BigDecimal amount) throws IOException {
        try {

            BigDecimal newAmountOrigin=userOrigin.getWallet().getBalance().subtract(amount);
            BigDecimal newAmountDestiny=userDestiny.getWallet().getBalance().add(amount);

            int updateOrigin=this.userService.updateBalanceUser(userOrigin.getWallet().getId(),newAmountOrigin);
            if(updateOrigin!=0){
                int updateDestiny=this.userService.updateBalanceUser(userDestiny.getWallet().getId(),newAmountDestiny);
                if(updateDestiny!=0){
                    return true;
                }else{
                    updateOrigin=this.userService.updateBalanceUser(userOrigin.getWallet().getId(),newAmountOrigin.add(amount));

                }
            }

        }catch (Exception e){
            throw e;

        }

        return false;
    }
}
