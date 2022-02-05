package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.dto.request.BalanceRequestDTO;
import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.BalanceResponseDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.exceptions.StatusNotFoundException;
import br.com.anderson_silva.Banking_system.services.UserService;
import br.com.anderson_silva.Banking_system.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/Bank")
public class UserController {

    private final UserService userService;
    private  final WalletService walletService;

    public UserController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @PostMapping("/clients")
    public ResponseEntity<UserResponseDTO> save(@RequestBody @Valid UserRequestDTO userDTO) throws StatusNotFoundException {
        UserResponseDTO userResponseDTO = this.userService.save(userDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);

    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UserRequestDTO userDTO, @PathVariable("id") Long id) {

        UserResponseDTO userResponseDTO =this.userService.update(userDTO,id);
        return  ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);

    }

    @PostMapping("/clients/transfer")
    public ResponseEntity<TransferResponseDTO> transfer(@RequestBody @Valid TransferRequestDTO transferReqTDO) throws IOException {

        TransferResponseDTO transferRespDTO=this.walletService.transfer(transferReqTDO);
        return ResponseEntity.status(HttpStatus.OK).body(transferRespDTO);

    }
    @GetMapping("/clients/balance/{id}")
    public ResponseEntity<BalanceResponseDTO> balance(@RequestBody @Valid BalanceRequestDTO balanceRequestTDO,@PathVariable("id") Long id){

        BalanceResponseDTO balanceResDTO=this.walletService.getBalance(balanceRequestTDO,id);
            return ResponseEntity.status(HttpStatus.OK).body(balanceResDTO);

    }

}
