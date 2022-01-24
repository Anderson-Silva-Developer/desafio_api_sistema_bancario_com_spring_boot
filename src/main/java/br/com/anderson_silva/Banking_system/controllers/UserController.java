package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.dto.request.TransferRequestTDO;
import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseTDO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.services.UserService;
import br.com.anderson_silva.Banking_system.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity<TransferResponseTDO> transfer(@RequestBody @Valid TransferRequestTDO transferReqTDO){
        TransferResponseTDO transferResTDO=new TransferResponseTDO();
        try {
            transferResTDO=this.walletService.transfer(transferReqTDO);
            return ResponseEntity.status(HttpStatus.OK).body(transferResTDO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transferResTDO);

    }

}
