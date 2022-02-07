package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import br.com.anderson_silva.Banking_system.exceptions.StatusInternalException;
import br.com.anderson_silva.Banking_system.exceptions.StatusNotFoundException;
import br.com.anderson_silva.Banking_system.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EncoderService encoderService;


    public UserService(UserRepository userRepository, EncoderService encoderService) {
        this.userRepository = userRepository;
        this.encoderService = encoderService;
    }

    public UserResponseDTO save(UserRequestDTO userDTO) {

        User user = userDTO.buildUser();
        user.setPassword(encoderService.encoder(user.getPassword()));
        user.getWallet().setPasswordTransaction(encoderService.encoder(userDTO.getPassword()));
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        user = userRepository.save(user);

        if (Objects.nonNull(user)) {
            userResponseDTO.setId(user.getId());
            return userResponseDTO;
        }
        throw new StatusInternalException(String.format("client %s not save", user.getFullName()));

    }

    public UserResponseDTO update(UserRequestDTO userDTO, Long id) {

        User newuser = userDTO.buildUser();
        newuser.setPassword(encoderService.encoder(newuser.getPassword()));
        newuser.getWallet().setPasswordTransaction(encoderService.encoder(userDTO.getPassword()));
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new StatusNotFoundException("client not found"));

        BeanUtils.copyProperties(newuser, user, "id", "wallet");
        user = userRepository.save(user);
        if (Objects.nonNull(user)) {
            userResponseDTO.setId(user.getId());
            userResponseDTO.setId(id);
            return userResponseDTO;
        }
        throw new StatusInternalException(String.format("client %s not update", user.getFullName()));


    }


    public User findByEmail(String email) {

        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new StatusNotFoundException("client not found"));

        return user;

    }

    public User findById(Long id) {

        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new StatusNotFoundException("client not found"));

        return user;

    }

    public User findByCpfCnpj(String cpfCnpj) {
        User user = this.userRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new StatusNotFoundException("client CpfCnpj not found"));

        return user;

    }

    public boolean updateBalanceUser(User userOrigin, User userDestiny, BigDecimal amount) {

        BigDecimal newAmountOrigin = userOrigin.getWallet().getBalance().subtract(amount);
        BigDecimal newAmountDestiny = userDestiny.getWallet().getBalance().add(amount);
        userOrigin.getWallet().setBalance(newAmountOrigin);
        userDestiny.getWallet().setBalance(newAmountDestiny);
        List<User> listUser = new ArrayList<>();
        listUser.add(userOrigin);
        listUser.add(userDestiny);
        List<User> users = this.userRepository.saveAll(listUser);

        if (Objects.nonNull(users)) {
            return true;
        }
        throw new StatusInternalException("Erro balance update");


    }


}
