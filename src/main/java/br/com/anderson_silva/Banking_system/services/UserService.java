package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.exceptions.RestExceptionHandler;
import br.com.anderson_silva.Banking_system.repositories.UserRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    private  final UserRepository userRepository;
    private final EncoderService encoderService;

    public UserService(UserRepository userRepository, EncoderService encoderService) {
        this.userRepository = userRepository;
        this.encoderService = encoderService;
    }

    public UserResponseDTO save(UserRequestDTO userDTO){

        User user=userDTO.buildUser();
        user.setPassword(encoderService.encoder(user.getPassword()));
        user.getWallet().setPasswordTransaction(encoderService.encoder(userDTO.getPassword()));
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(userRepository.save(user).getId());
        return userResponseDTO;

    }

    public  User findByEmail(String email) throws Exception {

        Optional<User> userOptional = this.userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            return this.userRepository.findByEmail(email).get();
        }
        throw new Exception();


    }

    public  User findByCpfCnpj(String cpfCnpj) throws Exception {
        Optional<User> userOptional=this.userRepository.findByCpfCnpj(cpfCnpj);
        if(userOptional.isPresent()){
            return  this.userRepository.findByCpfCnpj(cpfCnpj).get();
        }
        throw new Exception();


    }

    public int updateBalanceUser(Long id ,BigDecimal balance){
       return this.userRepository.updateBalance(id,balance);

    }



}
