package br.com.anderson_silva.Banking_system.controllers.bank;


import br.com.anderson_silva.Banking_system.controllers.BankController;
import br.com.anderson_silva.Banking_system.services.ServiceUserDetailsImpl;
import br.com.anderson_silva.Banking_system.services.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers = {BankController.class})
public class BankControllerTest {

    @MockBean
    protected WalletService walletService;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected ServiceUserDetailsImpl serviceUserDetails;



}