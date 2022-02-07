package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.dto.request.BalanceRequestDTO;
import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.BalanceResponseDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import br.com.anderson_silva.Banking_system.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/Bank")
public class BankController {


    private final WalletService walletService;

    public BankController(WalletService walletService) {
        this.walletService = walletService;
    }


    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDTO> transfer(@RequestBody @Valid TransferRequestDTO transferReqTDO) {

        TransferResponseDTO transferRespDTO = this.walletService.transfer(transferReqTDO);
        return ResponseEntity.status(HttpStatus.OK).body(transferRespDTO);

    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<BalanceResponseDTO> balance(@RequestBody @Valid BalanceRequestDTO balanceRequestTDO, @PathVariable("id") Long id) {

        BalanceResponseDTO balanceResDTO = this.walletService.getBalance(balanceRequestTDO, id);
        return ResponseEntity.status(HttpStatus.OK).body(balanceResDTO);

    }

}
