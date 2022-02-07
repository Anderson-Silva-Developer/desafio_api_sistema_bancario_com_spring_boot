package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

public class EndpoindUpdate extends UserControllerTest{
    String url="/Bank/clients/{id}";

    @Test
    @WithMockUser
    public void expectedSuccess_update() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");


        UserRequestDTO userRespDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");


//
//        Mockito.when(this.userService.update(any(),any())).thenReturn(userRespDTO);
//        new EndpointUtilTest().expected_success200_put(mockMvc, url, userReqDTO);
//
//        UserResponseDTO userResponseDTO1= this.userService.update(userReqDTO,1L);



    }
}
