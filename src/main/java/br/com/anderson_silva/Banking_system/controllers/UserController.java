package br.com.anderson_silva.Banking_system.controllers;

//import br.com.anderson_silva.Banking_system.bankTransaction.BankTransaction;
import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Bank")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userDTO){

            UserResponseDTO userResponseDTO =userService.save(userDTO);
            return  ResponseEntity.ok(userResponseDTO);

    }

//    @PostMapping("/transfer")
//    public ResponseEntity<?> transfer(@RequestBody @Valid ClientTransfer clientTransfer){
//           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//           TransferStatus transferStatus=new TransferStatus();
//           if(auth.isAuthenticated()){
//
//               boolean result=new BankTransaction().transfer(clientTransfer,auth,encoder);
//
//               HttpStatus status =(result)?HttpStatus.OK:HttpStatus.UNAUTHORIZED;
//               transferStatus =result?transferStatus.Accept():transferStatus.Recuse();
//               return  ResponseEntity.status(status).body(transferStatus) ;
//           }
//
//        transferStatus=transferStatus.Recuse();
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(transferStatus);
//
//    }
//
}
