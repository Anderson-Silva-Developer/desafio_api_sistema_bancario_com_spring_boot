package br.com.anderson_silva.Banking_system.controllers;


import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.services.ServiceUserDetailsImpl;
import br.com.anderson_silva.Banking_system.services.UserService;
import br.com.anderson_silva.Banking_system.services.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private WalletService walletService;

    @MockBean
     private ServiceUserDetailsImpl serviceUserDetails;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void expectedSuccess_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(userReqDTO)).thenReturn(userResponseDTO);
        this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                        .andExpect(status().is(200));
        UserResponseDTO userResp= this.userService.save(userReqDTO);
        assertEquals(userResp.getId(),1L);

    }

}