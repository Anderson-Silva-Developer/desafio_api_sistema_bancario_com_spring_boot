package br.com.anderson_silva.Banking_system.controllers;


import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Autowired
    private WebApplicationContext webApplicationContext;


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

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                        .andExpect(status().is(200));

        UserResponseDTO userResp= this.userService.save(userReqDTO);
        assertEquals(userResp.getId(),1L);

    }

    @Test
    @WithMockUser
    public void expectedSuccess_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
            .setAmountDestiny("10")
            .setCpfCnpjDestiny("957.051.460-43")
            .setTransactionPassword("@12345678");

        TransferResponseDTO transferRespDTO =new TransferResponseDTO()
            .setOperation("transferência")
            .setStatus(200)
            .setAmountDestiny(transferReqDTO.getAmountDestiny())
            .setCpfCnpjDestiny(transferReqDTO.getCpfCnpjDestiny())
            .setDetail("Sucesso");

        Mockito.when(this.walletService.transfer(any())).thenReturn(transferRespDTO);

        this.mockMvc.perform(post("/Bank/transfer")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .content(objectMapper.writeValueAsString(transferReqDTO)))
                        .andExpect(status().is(200));

        TransferResponseDTO transferResp= this.walletService.transfer(transferReqDTO);
        assertEquals(transferResp.getStatus(),200);

    }


}