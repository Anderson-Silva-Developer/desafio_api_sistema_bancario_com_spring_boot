package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import br.com.anderson_silva.Banking_system.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private  final UserRepository userRepository;

    public WalletService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Wallet create(User user){
        Wallet wallet=new Wallet();
        wallet.setPasswordTransaction(user.getPassword());
        return wallet;
    }
}
