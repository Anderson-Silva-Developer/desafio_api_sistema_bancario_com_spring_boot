package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.model.Client;
import br.com.anderson_silva.Banking_system.model.ClientTransfer;
import br.com.anderson_silva.Banking_system.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/Bank")
public class UserController {
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
    @GetMapping("/teste")
    public Client getClient(){
        return  c;
    }
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return user;
    }
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody ClientTransfer clientTransfer){
        LinkedHashMap origin= (LinkedHashMap) clientTransfer.getOrigin();
        LinkedHashMap destiny= (LinkedHashMap) clientTransfer.getDestiny();

        String amount= origin.get("transfer_amount_destiny").toString();
        BigDecimal v1 = new BigDecimal(amount.replaceAll("\\.", "").replace(",","."));
        System.out.println(c.getWallet().compareTo(new BigDecimal(v1.toString()))>=1);//compara se o saldo e maior ou igual
        c.setWallet(v1.add(c.getWallet()));
        DecimalFormat df = new DecimalFormat("###,##0.00");
        System.out.println(df.format(c.getWallet()));

        return ResponseEntity.ok(df.format(c.getWallet()));

    }


}
