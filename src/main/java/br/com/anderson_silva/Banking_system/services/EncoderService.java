package br.com.anderson_silva.Banking_system.services;

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
}
