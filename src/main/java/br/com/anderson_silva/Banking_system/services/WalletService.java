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
    private final TransactionService transactionService;

    public WalletService(UserService userService, EncoderService encoderService, NotificationService notificationService, TransactionService transactionService) {
        this.userService = userService;
        this.encoderService = encoderService;

        this.notificationService = notificationService;
        this.transactionService = transactionService;
    }

    public TransferResponseTDO transfer(TransferRequestTDO transferReqTDO) throws IOException {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            DecimalFormat df = new DecimalFormat("###,##0.00");

            if(!auth.isAuthenticated()){
                return  new TransferResponseTDO().TransferNotFaund(transferReqTDO.getAmountDestiny(),transferReqTDO.getCpfCnpjDestiny(),"usuário não autenticado");
            }

            User userOrigin=this.userService.findByEmail(auth.getName());
            User userDestiny=this.userService.findByCpfCnpj(transferReqTDO.getCpfCnpjDestiny());
            boolean isPassword=this.encoderService.checkPassword(transferReqTDO,userOrigin);
            BigDecimal balance=this.checkBalance(userOrigin,transferReqTDO);
            TransferResponseTDO transferResponseTDO = new TransferResponseTDO();
            if(balance.compareTo(new BigDecimal("0.0"))==0){
                return  new TransferResponseTDO().TransferNotFaund(transferReqTDO.getAmountDestiny(),transferReqTDO.getCpfCnpjDestiny(),"saldo insuficiente");
            }
            if(!isPassword){
                return  new TransferResponseTDO().TransferNotFaund(transferReqTDO.getAmountDestiny(),transferReqTDO.getCpfCnpjDestiny(),"usuário ou senha de transação incorreto");
            }
            if(userDestiny==null){
                return  new TransferResponseTDO().TransferNotFaund(transferReqTDO.getAmountDestiny(),transferReqTDO.getCpfCnpjDestiny(),"cpfCnpjDestiny não encontrado");
            }
            if(userOrigin.getTypeUser().equals("shopkeeper")){
                return  new TransferResponseTDO().TransferNotFaund(transferReqTDO.getAmountDestiny(),transferReqTDO.getCpfCnpjDestiny(),"usuário não autorizado");
            }

            boolean deposit=Deposit(userOrigin,userDestiny,balance);

            if(deposit) {
                    transferResponseTDO.setOperation("transferência");
                    transferResponseTDO.setStatus("Sucesso ");
                    transferResponseTDO.setAmountDestiny(df.format(balance));
                    transferResponseTDO.setCpfCnpjDestiny(userDestiny.getCpfCnpj());
                    transferResponseTDO.setDetail("Sucesso");
                    this.transactionService
                            .walletReport(userOrigin.getWallet().getId(),userDestiny.getWallet().getId(),balance,userOrigin.getWallet(),"transfer");
                    int code =this.notificationService.sendMail(userOrigin.getFullName(),df.format(balance),userDestiny.getEmail());
                    return  transferResponseTDO;

            }else {
                transferResponseTDO.setOperation("transferência");
                transferResponseTDO.setStatus("Falha");
                transferResponseTDO.setAmountDestiny(df.format(balance));
                transferResponseTDO.setCpfCnpjDestiny(userDestiny.getCpfCnpj());
                transferResponseTDO.setDetail("transferência não realizada");
                return transferResponseTDO;

            }


    }

    public BigDecimal checkBalance(User user, TransferRequestTDO transferRequestTDO){

        if(user!=null && !transferRequestTDO.getAmountDestiny().equals("")) {
            BigDecimal amountBigDecimal = new BigDecimal(transferRequestTDO.getAmountDestiny().replaceAll("\\.", "").replace(",", "."));
            boolean result = (user.getWallet().getBalance().compareTo(new BigDecimal(amountBigDecimal.toString())) >= 0);
            return result ? amountBigDecimal : new BigDecimal("0.0");
        }
        return  new BigDecimal("0.0");

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
