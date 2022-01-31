package br.com.anderson_silva.Banking_system.controllers;


import br.com.anderson_silva.Banking_system.services.ServiceUserDetailsImpl;
import br.com.anderson_silva.Banking_system.services.UserService;
import br.com.anderson_silva.Banking_system.services.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;
    @MockBean
    WalletService walletService;
    @MockBean
     private ServiceUserDetailsImpl serviceUserDetails;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;



}