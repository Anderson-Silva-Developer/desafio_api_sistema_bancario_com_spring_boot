package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.dto.request.BalanceRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.BalanceResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EndpointBalance extends UserControllerTest{


    @Test
    @WithMockUser
    public void expectedSuccess_balance() throws Exception {

        DecimalFormat df = new DecimalFormat("###,##0.00");

        BalanceRequestDTO balanceReqDTO=new BalanceRequestDTO()
                .setTransactionPassword("@12345678");

        BalanceResponseDTO balanceRespDTO=  new BalanceResponseDTO()
                .setOperation("Consulta de saldo")
                .setStatus(200)
                .setDetail("Saldo")
                .setBalance(df.format(new BigDecimal("1000")));


        Mockito.when(this.walletService.getBalance(any())).thenReturn(balanceRespDTO);

        this.mockMvc.perform(get("/Bank/balance")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .content(objectMapper.writeValueAsString(balanceReqDTO)))
                .andExpect(status().is(200));

        BalanceResponseDTO balanceResponseDTO= this.walletService.getBalance(balanceReqDTO);
        assertEquals(balanceResponseDTO.getStatus(),200);


    }
}
