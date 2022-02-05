package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.exceptions.StatusNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        if(Objects.nonNull(user) && !password.equals("")) {
            return encoder.matches(password, user.getWallet().getPasswordTransaction());
        }
        throw new StatusNotFoundException("usuário ou senha de transação incorreto");

    }
}
