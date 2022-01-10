package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.bankTransaction.BankTransaction;
import br.com.anderson_silva.Banking_system.model.Client;
import br.com.anderson_silva.Banking_system.model.ClientTransfer;
import br.com.anderson_silva.Banking_system.model.User;
import br.com.anderson_silva.Banking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/Bank")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    Client c=new Client();

    @GetMapping("/")
    public Client hello(){
        c.setFull_name("Anderson Silva");
        c.setId(1L);
        c.setCpf_cnpj("000.999.000-12");
        c.setEmail("teste@gmail.com");
        c.setPassword("123");
        c.setType_user("client");

        return  c;
    }
    @GetMapping("/list")
    public List<User> getClient(){
        return  this.userRepository.findAll();
    }
    @PostMapping("/register")
    public User register(@RequestBody User user){
        //OBS:verificar todos os campos
       return this.userRepository.save(user);

    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody ClientTransfer clientTransfer){
       try {
           if(!clientTransfer.getTransfer_amount_destiny().equals("")&&
                   !clientTransfer.getCpf_cnpj_destiny().equals("")&&
                   !clientTransfer.getCpf_cnpj_origin().equals("")&&
                   !clientTransfer.getEmail().equals("")&&
                   !clientTransfer.getPassword().equals("")
           ){

               boolean result=new BankTransaction().transfer(clientTransfer);


           }else{
               System.out.println("dados não ok");
           }

       }catch (Exception e){
           return  ResponseEntity.ok(e.getMessage());
       }


        return  ResponseEntity.ok(c.getWallet().toString());

    }


}
