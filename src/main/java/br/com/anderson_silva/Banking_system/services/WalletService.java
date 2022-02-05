package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.BalanceRequestDTO;
import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.BalanceResponseDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import br.com.anderson_silva.Banking_system.exceptions.StatusNotFoundException;
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

               }

               return new TransferResponseDTO()
                           .setOperation("transferência")
                           .setStatus(HttpStatus.NOT_FOUND.value())
                           .setAmountDestiny(transferReqTDO.getAmountDestiny())
                           .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                           .setDetail("transferência não realizada, confira os campos [transactionPassword, cpfCnpjDestiny, amountDestiny");


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

        if(Objects.nonNull(user) && !transferRequestTDO.getAmountDestiny().equals("")) {
            BigDecimal amountBigDecimal = new BigDecimal(transferRequestTDO.getAmountDestiny().replaceAll("\\.", "").replace(",", "."));
            boolean result = (user.getWallet().getBalance().compareTo(new BigDecimal(amountBigDecimal.toString())) >= 0);
            return result ? amountBigDecimal : new BigDecimal("0.0");
        }
        return  new BigDecimal("0.0");

    }

    public boolean Deposit(User userOrigin,User userDestiny,BigDecimal amount) throws Exception {



            BigDecimal newAmountOrigin=userOrigin.getWallet().getBalance().subtract(amount);
            BigDecimal newAmountDestiny=userDestiny.getWallet().getBalance().add(amount);

            boolean updateOrigin=this.userService.updateBalanceUser(userOrigin.getWallet(),newAmountOrigin);
            boolean updateDestiny=this.userService.updateBalanceUser(userDestiny.getWallet(),newAmountDestiny);

            if(updateOrigin && updateDestiny) {
                return true;
            }
            if(!updateOrigin ||!updateDestiny){
                this.userService.updateBalanceUser(userOrigin.getWallet(),newAmountOrigin.add(amount));
                this.userService.updateBalanceUser(userDestiny.getWallet(),newAmountDestiny.subtract(amount));
            }
            return false;

    }

    public BalanceResponseDTO getBalance(BalanceRequestDTO balanceReqDTO,Long id){

        User userOrigin=this.userService.findById(id);
        boolean isPassword=this.encoderService.checkPassword(balanceReqDTO.getTransactionPassword(),userOrigin);

        if(!isPassword){
            throw new  StatusNotFoundException("usuário ou senha de transação incorreto");
        }

        Wallet wallet=this.walletRepository.findById(userOrigin.getWallet().getId())
                .orElseThrow(()-> new StatusNotFoundException("wallet not found"));

        DecimalFormat df = new DecimalFormat("###,##0.00");
        return  new BalanceResponseDTO()
                .setOperation("Consulta de saldo")
                .setDetail("Saldo")
                .setBalance(df.format(wallet.getBalance()));

    }

}
