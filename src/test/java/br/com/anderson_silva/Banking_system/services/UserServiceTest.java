package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.AplicationConfigTest;
import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("UserServiceTest ")
class UserServiceTest extends AplicationConfigTest {


    private UserRepository userRepository=Mockito.mock(UserRepository.class);
    private EncoderService encoderService=Mockito.mock(EncoderService.class);

    @Autowired
    private  UserService userService;

    @Test
    @DisplayName("expected to save the user")
    void saveTest(){
        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emaildsdstqwaaveaw@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");

        UserResponseDTO userRespDTO=new UserResponseDTO();
        userRespDTO.setId(101L);

        Mockito.when(this.userService.save(userReqDTO)).thenReturn(userRespDTO);
        UserResponseDTO userResponseDTO= this.userService.save(userReqDTO);
        System.out.println(userResponseDTO);
        assertEquals(userResponseDTO.getId(),101);




    }



}