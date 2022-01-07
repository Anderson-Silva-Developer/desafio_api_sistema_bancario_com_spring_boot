package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.model.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/Bank")
public class UserController {
    @GetMapping("/")
    public Client hello(){
        Client c=new Client();
        c.setFull_name("Anderson Silva");
        c.setId(1L);
        c.setCpf_cnpj("000.999.000-12");
        c.setEmail("teste@gmail.com");
        c.setPassword("123");
        c.setType_user("client");
        BigDecimal v=new BigDecimal("1.000").setScale(4, RoundingMode.HALF_EVEN);
        c.setWallet(c.getWallet().add(v));

        return  c;
    }
}
