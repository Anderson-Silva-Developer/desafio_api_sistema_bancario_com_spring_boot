package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.bankTransaction.BankTransaction;
import br.com.anderson_silva.Banking_system.model.ClientTransfer;
import br.com.anderson_silva.Banking_system.model.User;
import br.com.anderson_silva.Banking_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Bank")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("/todos")
    public  List<User> todos(){
        List<User> users=this.userRepository.findAll();
        return users;
    }


    @GetMapping("/valid")
    public ResponseEntity<Boolean> validUser(@RequestParam String email,@RequestParam String password){
        try {
            Optional<User>  optUsuario= this.userRepository.findByEmail(email);
            if(optUsuario.isEmpty()){
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
            }

            User user=optUsuario.get();
            boolean valid=encoder.matches(password,user.getPassword());
            HttpStatus status =(valid)?HttpStatus.OK:HttpStatus.UNAUTHORIZED;
            return  ResponseEntity.status(status).body(valid);

        }catch (Exception e){
            throw e;

        }



    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user){
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            return  ResponseEntity.ok(userRepository.save(user));
        }catch (Exception e){
            throw e;
        }

    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody ClientTransfer clientTransfer){
       try {
               boolean result=new BankTransaction().transfer(clientTransfer);
               return  result?ResponseEntity.ok("Transferência concluída !!"):ResponseEntity.ok("Transferência cancelada !!");

       }catch (Exception e){
           return  ResponseEntity.ok("Transferência não realizada cancelada !!");
       }


    }


}
