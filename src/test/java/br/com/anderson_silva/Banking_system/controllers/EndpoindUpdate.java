package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.controllers.util.EndpointUtilTest;
import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

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

        UserRequestDTO userReqDTO_=new UserRequestDTO();
        userReqDTO_.setFullName("cliente alterado")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");

        UserResponseDTO userRespDTO=new UserResponseDTO(1L);



        ResultActions resultput = new EndpointUtilTest().expected_success200_put(mockMvc, url, userReqDTO);
        System.out.println(resultput.andReturn().getResponse().getContentAsString());


//
        UserResponseDTO userResponseDTO1= this.userService.update(userReqDTO,1L);



    }
}
