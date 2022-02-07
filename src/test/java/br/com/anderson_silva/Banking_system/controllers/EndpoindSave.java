package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.controllers.util.EndpointUtilTest;
import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

public class EndpoindSave extends UserControllerTest {
    String errorFullName="o campo fullName não pode ficar em branco";
    String errorEmail="o campo email não pode ficar em branco ou formato incorreto";
    String errorCpfCnpj="campo cpfCnpj inválido formato aceito ex:cpf[xxx.xxx.xxx-xx] ou cnpj[xxx.xxx.xxx-xx]";
    String errorTypeUser="O campo typeUser aceita apenas os valores [client] ou [shopkeeper]";
    String errorPassword="o campo password deve ficar entre 8 e 16 caracteres";
    String url="/Bank/clients";


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

        new EndpointUtilTest().expected_success201_post(mockMvc, url, userReqDTO);

        UserResponseDTO userResp= this.userService.save(userReqDTO);
        assertEquals(userResp.getId(),1L);

    }
    @Test
    public void expected_failure_empty_field_fullName_register() throws Exception {
        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO
                .setFullName("")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorFullName));

    }
    @Test
    public void expected_failure_null_field_fullName_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO

                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error,errorFullName));

    }

    @Test
    public void expected_failure_empty_field_email_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error,errorEmail));

    }
    @Test
    public void expected_failure_null_field_email_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO
                .setFullName("cliente 01")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");
        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorEmail));

    }
    @Test
    public void expected_failure_invalid_field_email_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("fjbbfdbfjbdjbjfdkf")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");
        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorEmail));

    }
    @Test
    public void expected_failure_empty_field_CpfCnpj_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("")
                .setTypeUser("client")
                .setPassword("@12345678");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorCpfCnpj));

    }
    @Test
    public void expected_failure_null_field_CpfCnpj_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setTypeUser("client")
                .setPassword("@12345678");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorCpfCnpj));

    }
    @Test
    public void expected_failure_invalid_field_CpfCnpj_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("886-55656-56-565-6")
                .setTypeUser("client")
                .setPassword("@12345678");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorCpfCnpj));

    }

    @Test
    public void expected_failure_empty_field_typeUser_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("")
                .setPassword("@12345678");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorTypeUser));

    }



    @Test
    public void expected_failure_null_field_typeUser_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setPassword("@12345678");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorTypeUser));

    }
    @Test
    public void expected_failure_invalid_field_typeUser_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("smdsmdsm")
                .setPassword("@12345678");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorTypeUser));

    }

    @Test
    public void expected_failure_empty_field_password_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("");

        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorPassword));

    }
    @Test
    public void expected_failure_null_field_password_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client");
        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorPassword));

    }
    @Test
    public void expected_failure_invalid_field_password_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("fdf");
        String error=new EndpointUtilTest().expected_failure400_post(mockMvc,url,userReqDTO);
        assertTrue(StringUtils.contains(error, errorPassword));

    }
}
