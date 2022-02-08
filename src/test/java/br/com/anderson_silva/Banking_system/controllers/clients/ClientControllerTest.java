package br.com.anderson_silva.Banking_system.controllers.clients;

import br.com.anderson_silva.Banking_system.controllers.ClientController;
import br.com.anderson_silva.Banking_system.services.ServiceUserDetailsImpl;
import br.com.anderson_silva.Banking_system.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ClientController.class})
public class ClientControllerTest {
    @MockBean
    protected UserService userService;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected ServiceUserDetailsImpl serviceUserDetails;
}
