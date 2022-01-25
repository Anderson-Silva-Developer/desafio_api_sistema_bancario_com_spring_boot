package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncoderService {
    private final PasswordEncoder encoder;

    public EncoderService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String encoder(String password){
        return encoder.encode(password);

    }
    public  Boolean checkPassword(String password, User user){
        if(user!=null && !password.equals("")) {
            System.out.println(user.getWallet().getPasswordTransaction());
            return encoder.matches(password, user.getWallet().getPasswordTransaction());
        }
        return false;
    }
}
