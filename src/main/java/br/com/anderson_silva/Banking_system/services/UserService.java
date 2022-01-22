package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import br.com.anderson_silva.Banking_system.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private  final UserRepository userRepository;
    private final WalletService walletService;
    private final EncoderService encoderService;

    public UserService(UserRepository userRepository, WalletService walletService, EncoderService encoderService) {
        this.userRepository = userRepository;
        this.walletService = walletService;
        this.encoderService = encoderService;
    }

    public UserResponseDTO save(UserRequestDTO userDTO){
        User user=userDTO.buildUser();
        Wallet wallet=walletService.create(user);
        user.setPassword(encoderService.encoder(user.getPassword()));
        wallet.setPasswordTransaction(encoderService.encoder(user.getPassword()));
        user.setWallet(wallet);
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(userRepository.save(user).getId());

        return userResponseDTO;

    }

}
