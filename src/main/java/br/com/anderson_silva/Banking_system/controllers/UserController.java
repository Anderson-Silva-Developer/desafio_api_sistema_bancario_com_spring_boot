package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.dto.request.BalanceRequestDTO;
import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.BalanceResponseDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
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

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO userDTO){

            UserResponseDTO userResponseDTO =this.userService.save(userDTO);
            return  ResponseEntity.ok(userResponseDTO);

    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDTO> transfer(@RequestBody @Valid TransferRequestDTO transferReqTDO) throws IOException {

        TransferResponseDTO transferRespDTO=this.walletService.transfer(transferReqTDO);
        return ResponseEntity.status(transferRespDTO.getStatus()).body(transferRespDTO);

    }
    @GetMapping("/balance")
    public ResponseEntity<BalanceResponseDTO> balance(@RequestBody @Valid BalanceRequestDTO balanceRequestTDO){
            BalanceResponseDTO balanceResDTO=new BalanceResponseDTO();
        try {
            balanceResDTO=this.walletService.getBalance(balanceRequestTDO);
            return ResponseEntity.status(HttpStatus.OK).body(balanceResDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(balanceResDTO);

    }

}
