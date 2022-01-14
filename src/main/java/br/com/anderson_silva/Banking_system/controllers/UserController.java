package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.bankTransaction.BankTransaction;
import br.com.anderson_silva.Banking_system.exceptions.EntityNotFoundException;
import br.com.anderson_silva.Banking_system.exceptions.StandardError;
import br.com.anderson_silva.Banking_system.model.ClientTransfer;
import br.com.anderson_silva.Banking_system.model.TransferStatus;
import br.com.anderson_silva.Banking_system.model.User;
import br.com.anderson_silva.Banking_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/Bank")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user){

            user.setPassword(encoder.encode(user.getPassword()));
            user.setTransaction_password(encoder.encode(user.getTransaction_password()));

            return  new ResponseEntity<User>(userRepository.save(user),HttpStatus.CREATED);



    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody @Valid ClientTransfer clientTransfer){
           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           TransferStatus transferStatus=new TransferStatus();
           if(auth.isAuthenticated()){

               boolean result=new BankTransaction().transfer(clientTransfer,auth,encoder);

               HttpStatus status =(result)?HttpStatus.OK:HttpStatus.UNAUTHORIZED;
               transferStatus =result?transferStatus.Accept():transferStatus.Recuse();
               return  ResponseEntity.status(status).body(transferStatus) ;
           }

        transferStatus=transferStatus.Recuse();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(transferStatus);

    }

}
