package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.exceptions.StatusInternalException;
import br.com.anderson_silva.Banking_system.exceptions.StatusNotFoundException;
import br.com.anderson_silva.Banking_system.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
        user = userRepository.save(user);

        if (Objects.nonNull(user.getId())) {
            return  new UserResponseDTO(user.getId());
        }
        throw new StatusInternalException(String.format("client %s not save", user.getFullName()));

    }

    public UserResponseDTO update(UserRequestDTO userDTO, Long id) {

        User newuser = userDTO.buildUser();
        newuser.setPassword(encoderService.encoder(newuser.getPassword()));
        newuser.getWallet().setPasswordTransaction(encoderService.encoder(userDTO.getPassword()));
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new StatusNotFoundException("client not found"));

        BeanUtils.copyProperties(newuser, user, "id", "wallet");
        userRepository.save(user);
        return new UserResponseDTO(user.getId());

}


    public User findByEmail(String email) {

        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new StatusNotFoundException("client not found"));

    }

    public User findById(Long id) {

        return this.userRepository.findById(id)
                .orElseThrow(() -> new StatusNotFoundException("client not found"));

    }

    public User findByCpfCnpj(String cpfCnpj) {

        return this.userRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new StatusNotFoundException("client CpfCnpj not found"));

    }

    public boolean updateAllUsers( List<User> listUser) {

        List<User> users = this.userRepository.saveAll(listUser);
        if (Objects.nonNull(users)) {
            return true;
        }
        throw new StatusInternalException("Erro balance update");


    }


}
