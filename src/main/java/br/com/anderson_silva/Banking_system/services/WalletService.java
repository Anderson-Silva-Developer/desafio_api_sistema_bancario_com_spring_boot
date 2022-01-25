package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.BalanceRequestDTO;
import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.BalanceResponseDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import br.com.anderson_silva.Banking_system.repositories.WalletRepository;
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
    private final WalletRepository walletRepository;

    public WalletService(UserService userService, EncoderService encoderService, NotificationService notificationService, TransactionService transactionService, WalletRepository walletRepository) {
        this.userService = userService;
        this.encoderService = encoderService;

        this.notificationService = notificationService;
        this.transactionService = transactionService;
        this.walletRepository = walletRepository;
    }

    public TransferResponseDTO transfer(TransferRequestDTO transferReqTDO) throws IOException {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            DecimalFormat df = new DecimalFormat("###,##0.00");

            if(!auth.isAuthenticated()){
                return  new TransferResponseDTO()
                        .setOperation("transferência")
                        .setStatus("Falha")
                        .setAmountDestiny(transferReqTDO.getAmountDestiny())
                        .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                        .setDetail("usuário não autenticado");
            }

            User userOrigin=this.userService.findByEmail(auth.getName());
            User userDestiny=this.userService.findByCpfCnpj(transferReqTDO.getCpfCnpjDestiny());
            boolean isPassword=this.encoderService.checkPassword(transferReqTDO.getTransactionPassword(),userOrigin);
            BigDecimal balance=this.checkBalance(userOrigin,transferReqTDO);
            TransferResponseDTO transferResponseTDO = new TransferResponseDTO();

            if(balance.compareTo(new BigDecimal("0.0"))==0){
                return  new TransferResponseDTO()
                .setOperation("transferência")
                .setStatus("Falha")
                .setAmountDestiny(transferReqTDO.getAmountDestiny())
                .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                .setDetail("saldo insuficiente");

            }
            if(!isPassword){
                return  new TransferResponseDTO()
                        .setOperation("transferência")
                        .setStatus("Falha")
                        .setAmountDestiny(transferReqTDO.getAmountDestiny())
                        .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                        .setDetail("usuário ou senha de transação incorreto");

            }
            if(userDestiny==null){
                return  new TransferResponseDTO()
                        .setOperation("transferência")
                        .setStatus("Falha")
                        .setAmountDestiny(transferReqTDO.getAmountDestiny())
                        .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                        .setDetail("cpfCnpjDestiny não encontrado");
            }
            if(userOrigin.getTypeUser().equals("shopkeeper")){
                return  new TransferResponseDTO()
                        .setOperation("transferência")
                        .setStatus("Falha")
                        .setAmountDestiny(transferReqTDO.getAmountDestiny())
                        .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                        .setDetail("usuário não autorizado");

            }

            boolean deposit=Deposit(userOrigin,userDestiny,balance);

            if(deposit) {

               TransferResponseDTO  transferResponseDTO=new TransferResponseDTO()
                        .setOperation("transferência")
                        .setStatus("Sucesso")
                        .setAmountDestiny(transferReqTDO.getAmountDestiny())
                        .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                        .setDetail("Sucesso");

                    this.transactionService
                            .walletReport(userOrigin.getWallet().getId(),userDestiny.getWallet().getId(),balance,userOrigin.getWallet(),"transfer");
                    int code =this.notificationService.sendMail(userOrigin.getFullName(),df.format(balance),userDestiny.getEmail());
                    return  transferResponseTDO;

            }else {

                return  new TransferResponseDTO()
                        .setOperation("transferência")
                        .setStatus("Falha")
                        .setAmountDestiny(transferReqTDO.getAmountDestiny())
                        .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                        .setDetail("transferência não realizada");

            }


    }

    public BigDecimal checkBalance(User user, TransferRequestDTO transferRequestTDO){

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

    public BalanceResponseDTO getBalance(BalanceRequestDTO balanceReqDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userOrigin=this.userService.findByEmail(auth.getName());
        boolean isPassword=this.encoderService.checkPassword(balanceReqDTO.getTransactionPassword(),userOrigin);
        if(!isPassword){
            return  new BalanceResponseDTO()
                    .setOperation("Consulta de saldo")
                    .setStatus("Falha")
                    .setDetail("usuário ou senha de transação incorreto")
                    .setBalance("não disponivel");
        }

        if(userOrigin==null){
            return  new BalanceResponseDTO()
                    .setOperation("Consulta de saldo")
                    .setStatus("Falha")
                    .setDetail("usuário não encontrado")
                    .setBalance("não disponivel");
        }
        Wallet wallet=this.walletRepository.findById(userOrigin.getWallet().getId()).get();

        DecimalFormat df = new DecimalFormat("###,##0.00");
        return  new BalanceResponseDTO()
                .setOperation("Consulta de saldo")
                .setStatus("Sucesso")
                .setDetail("Saldo")
                .setBalance(df.format(wallet.getBalance()));

    }

}
