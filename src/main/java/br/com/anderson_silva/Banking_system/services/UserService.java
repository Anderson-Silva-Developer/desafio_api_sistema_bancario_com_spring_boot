package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import br.com.anderson_silva.Banking_system.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

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
        user=userRepository.save(user);
        if(!Objects.isNull(user)){
            userResponseDTO.setId(user.getId());
            userResponseDTO.setStatus(200);
            return userResponseDTO;
        }
        userResponseDTO.setStatus(400);
        return userResponseDTO;

    }
    public UserResponseDTO update(UserRequestDTO userDTO,Long id) throws Exception {

        User user=userDTO.buildUser();
        user.setPassword(encoderService.encoder(user.getPassword()));
        user.getWallet().setPasswordTransaction(encoderService.encoder(userDTO.getPassword()));
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        Optional<User> userOptional=this.userRepository.findById(id);
        if(userOptional.isPresent()){

            BeanUtils.copyProperties(user,userOptional.get(),"id","wallet");
            userResponseDTO.setId(userRepository.save(userOptional.get()).getId());
            userResponseDTO.setStatus(200);
            userResponseDTO.setId(id);
            return userResponseDTO;
        }
        userResponseDTO.setStatus(404);
        userResponseDTO.setId(id);
        return userResponseDTO;
//


    }


    public  User findByEmail(String email) throws Exception {

        Optional<User> userOptional = this.userRepository.findByEmail(email);

        if(userOptional.isPresent()){
            return userOptional.get();
        }
        throw new Exception();


    }

    public  User findByCpfCnpj(String cpfCnpj) throws Exception {
        Optional<User> userOptional=this.userRepository.findByCpfCnpj(cpfCnpj);
        if(userOptional.isPresent()){
            return  userOptional.get();
        }
        throw new Exception();


    }

    public boolean updateBalanceUser(Wallet wallet , BigDecimal balance) throws Exception {

        Optional<User> userOptional=this.userRepository.findByWallet(wallet);

        if(userOptional.isPresent()){
            userOptional.get().getWallet().setBalance(balance);
            this.userRepository.save(userOptional.get());
            return true;
        }
        throw new Exception();

    }



}
