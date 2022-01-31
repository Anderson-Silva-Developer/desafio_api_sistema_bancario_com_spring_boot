package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EndpoindRegister extends UserControllerTest{


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
    public void expected_failure_empty_field_fullName_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO
                .setFullName("")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "o campo fullName não pode ficar em branco"));

    }
    @Test
    public void expected_failure_null_field_fullName_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO

                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "o campo fullName não pode ficar em branco"));

    }

    @Test
    public void expected_failure_empty_field_email_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "o campo email não pode ficar em branco ou formato incorreto"));

    }
    @Test
    public void expected_failure_null_field_email_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO
                .setFullName("cliente 01")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "o campo email não pode ficar em branco ou formato incorreto"));

    }
    @Test
    public void expected_failure_invalid_field_email_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("fjbbfdbfjbdjbjfdkf")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "o campo email não pode ficar em branco ou formato incorreto"));

    }
    @Test
    public void expected_failure_empty_field_CpfCnpj_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("")
                .setTypeUser("client")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "campo cpfCnpj inválido formato aceito ex:cpf[xxx.xxx.xxx-xx] ou cnpj[xxx.xxx.xxx-xx]"));

    }
    @Test
    public void expected_failure_null_field_CpfCnpj_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setTypeUser("client")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "campo cpfCnpj inválido formato aceito ex:cpf[xxx.xxx.xxx-xx] ou cnpj[xxx.xxx.xxx-xx]"));

    }
    @Test
    public void expected_failure_invalid_field_CpfCnpj_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("886-55656-56-565-6")
                .setTypeUser("client")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "campo cpfCnpj inválido formato aceito ex:cpf[xxx.xxx.xxx-xx] ou cnpj[xxx.xxx.xxx-xx]"));

    }

    @Test
    public void expected_failure_empty_field_typeUser_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "O campo typeUser aceita apenas os valores [client] ou [shopkeeper]"));

    }



    @Test
    public void expected_failure_null_field_typeUser_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "O campo typeUser aceita apenas os valores [client] ou [shopkeeper]"));

    }
    @Test
    public void expected_failure_invalid_field_typeUser_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("smdsmdsm")
                .setPassword("@12345678");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "O campo typeUser aceita apenas os valores [client] ou [shopkeeper]"));

    }

    @Test
    public void expected_failure_empty_field_password_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "o campo password deve ficar entre 8 e 16 caracteres"));

    }
    @Test
    public void expected_failure_null_field_password_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client");

        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "o campo password deve ficar entre 8 e 16 caracteres"));

    }
    @Test
    public void expected_failure_invalid_field_password_register() throws Exception {

        UserRequestDTO userReqDTO=new UserRequestDTO();
        userReqDTO.setFullName("cliente 01")
                .setEmail("emailteste@gmail.com")
                .setCpfCnpj("957.051.460-43")
                .setTypeUser("client")
                .setPassword("fdf");
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(1L);

        Mockito.when(this.userService.save(any())).thenReturn(userResponseDTO);
        String error=this.mockMvc.perform(post("/Bank/register")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        this.userService.save(userReqDTO);
        assertTrue(StringUtils.contains(error, "o campo password deve ficar entre 8 e 16 caracteres"));

    }
}
