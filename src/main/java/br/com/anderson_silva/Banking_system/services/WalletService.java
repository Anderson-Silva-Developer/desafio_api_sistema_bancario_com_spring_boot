package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.BalanceRequestDTO;
import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.BalanceResponseDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import br.com.anderson_silva.Banking_system.repositories.WalletRepository;
import br.com.anderson_silva.Banking_system.validator.ValidatorTransfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

@Service
public class WalletService {
    private final UserService  userService;
    private final EncoderService encoderService;
    private final NotificationService notificationService;
    private final TransactionService transactionService;
    private final WalletRepository walletRepository;
    private   ValidatorTransfer validatorTransfer=new ValidatorTransfer();

    public WalletService(UserService userService, EncoderService encoderService, NotificationService notificationService, TransactionService transactionService, WalletRepository walletRepository) {
        this.userService = userService;
        this.encoderService = encoderService;

        this.notificationService = notificationService;
        this.transactionService = transactionService;
        this.walletRepository = walletRepository;

    }

    public TransferResponseDTO transfer(TransferRequestDTO transferReqTDO) throws IOException {

           try {

               Authentication auth = SecurityContextHolder.getContext().getAuthentication();
               DecimalFormat df = new DecimalFormat("###,##0.00");
               User userOrigin=this.userService.findByEmail(auth.getName());
               User userDestiny = this.userService.findByCpfCnpj(transferReqTDO.getCpfCnpjDestiny());
               boolean isPassword = this.encoderService.checkPassword(transferReqTDO.getTransactionPassword(), userOrigin);
               BigDecimal balance = this.checkBalance(userOrigin, transferReqTDO);

               TransferResponseDTO transferResponseTDO = this.validatorTransfer.validatorRequestTransfer(auth, transferReqTDO, userOrigin, userDestiny, isPassword, balance);

               if (!Objects.isNull(transferResponseTDO)) {
                   return transferResponseTDO;

               }

               boolean deposit = Deposit(userOrigin, userDestiny, balance);

               if (deposit) {

                   TransferResponseDTO transferRespTDO = new TransferResponseDTO()
                           .setOperation("transferência")
                           .setStatus(200)
                           .setAmountDestiny(transferReqTDO.getAmountDestiny())
                           .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                           .setDetail("Sucesso");

                   this.transactionService
                           .walletReport(userOrigin.getWallet().getId(), userDestiny.getWallet().getId(), balance, userOrigin.getWallet(), "transfer");
                   int code = this.notificationService.sendMail(userOrigin.getFullName(), df.format(balance), userDestiny.getEmail());

                   return transferRespTDO;

               } else {

                   return new TransferResponseDTO()
                           .setOperation("transferência")
                           .setStatus(HttpStatus.NOT_FOUND.value())
                           .setAmountDestiny(transferReqTDO.getAmountDestiny())
                           .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                           .setDetail("transferência não realizada, confira os campos [transactionPassword, cpfCnpjDestiny, amountDestiny");

               }
           }catch (Exception e){
               return new TransferResponseDTO()
                       .setOperation("transferência")
                       .setStatus(HttpStatus.NOT_FOUND.value())
                       .setAmountDestiny(transferReqTDO.getAmountDestiny())
                       .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                       .setDetail("transferência não realizada, confira os campos [transactionPassword, cpfCnpjDestiny, amountDestiny");
           }

    }

    public BigDecimal checkBalance(User user, TransferRequestDTO transferRequestTDO){

        if(!Objects.isNull(user) && !transferRequestTDO.getAmountDestiny().equals("")) {
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
                    this.userService.updateBalanceUser(userOrigin.getWallet().getId(),newAmountOrigin.add(amount));

                }
            }

        }catch (Exception e){
            throw e;

        }

        return false;
    }

    public BalanceResponseDTO getBalance(BalanceRequestDTO balanceReqDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userOrigin=this.userService.findByEmail(auth.getName());
        boolean isPassword=this.encoderService.checkPassword(balanceReqDTO.getTransactionPassword(),userOrigin);
        if(!isPassword){
            return  new BalanceResponseDTO()
                    .setOperation("Consulta de saldo")
                    .setStatus(HttpStatus.BAD_REQUEST.value())
                    .setDetail("usuário ou senha de transação incorreto")
                    .setBalance("não disponivel");
        }

        if(Objects.isNull(userOrigin)){
            return  new BalanceResponseDTO()
                    .setOperation("Consulta de saldo")
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setDetail("usuário não encontrado")
                    .setBalance("não disponivel");
        }
        Wallet wallet=this.walletRepository.findById(userOrigin.getWallet().getId()).get();

        DecimalFormat df = new DecimalFormat("###,##0.00");
        return  new BalanceResponseDTO()
                .setOperation("Consulta de saldo")
                .setStatus(200)
                .setDetail("Saldo")
                .setBalance(df.format(wallet.getBalance()));

    }

}
