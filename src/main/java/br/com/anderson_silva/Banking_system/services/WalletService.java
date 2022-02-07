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
    private final UserService userService;
    private final EncoderService encoderService;
    private final NotificationService notificationService;
    private final TransactionService transactionService;
    private final WalletRepository walletRepository;
    private ValidatorTransfer validatorTransfer = new ValidatorTransfer();

    public WalletService(UserService userService, EncoderService encoderService, NotificationService notificationService, TransactionService transactionService, WalletRepository walletRepository) {
        this.userService = userService;
        this.encoderService = encoderService;

        this.notificationService = notificationService;
        this.transactionService = transactionService;
        this.walletRepository = walletRepository;

    }

    public TransferResponseDTO transfer(TransferRequestDTO transferReqTDO) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userOrigin = this.userService.findByEmail(auth.getName());
        User userDestiny = this.userService.findByCpfCnpj(transferReqTDO.getCpfCnpjDestiny());
        this.encoderService.checkPassword(transferReqTDO.getTransactionPassword(), userOrigin);
        BigDecimal balance = this.checkBalance(userOrigin, transferReqTDO);

        TransferResponseDTO transferResponseTDO = this.validatorTransfer.validatorRequestTransfer(auth, transferReqTDO, userOrigin, userDestiny);

        if (Objects.nonNull(transferResponseTDO)) {
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
            this.notificationService.sendMail(userOrigin.getFullName(),getDecimalFormat(balance), userDestiny.getEmail());

            return transferRespTDO;

        }

        return new TransferResponseDTO()
                .setOperation("transferência")
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setAmountDestiny(transferReqTDO.getAmountDestiny())
                .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                .setDetail("transferência não realizada, confira os campos [transactionPassword, cpfCnpjDestiny, amountDestiny");

    }

    public BigDecimal checkBalance(User user, TransferRequestDTO transferRequestTDO) {

        if (Objects.nonNull(user) && !transferRequestTDO.getAmountDestiny().equals("")) {
            BigDecimal amountBigDecimal = new BigDecimal(transferRequestTDO.getAmountDestiny().replaceAll("\\.", "").replace(",", "."));
            boolean result = (user.getWallet().getBalance().compareTo(new BigDecimal(amountBigDecimal.toString())) >= 0);
            if (result) {
                return amountBigDecimal;
            }
            throw new StatusNotFoundException("Saldo insuficiente");
        }
        throw new StatusNotFoundException("Saldo insuficiente");

    }

    public boolean Deposit(User userOrigin, User userDestiny, BigDecimal amount) {

        return this.userService.updateBalanceUser(userOrigin, userDestiny, amount);

    }


    public BalanceResponseDTO getBalance(BalanceRequestDTO balanceReqDTO, Long id) {

        User user = this.userService.findById(id);
        this.encoderService.checkPassword(balanceReqDTO.getTransactionPassword(), user);

        Wallet wallet = this.walletRepository.findById(user.getWallet().getId())
                .orElseThrow(() -> new StatusNotFoundException("wallet not found"));


        return new BalanceResponseDTO()
                .setOperation("Consulta de saldo")
                .setDetail("Saldo")
                .setBalance(getDecimalFormat(wallet.getBalance()));

    }

    private String  getDecimalFormat(BigDecimal balance) {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        return df.format(balance);
    }


}
