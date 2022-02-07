package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.controllers.util.EndpointUtilTest;
import br.com.anderson_silva.Banking_system.dto.request.BalanceRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.BalanceResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

public class EndpointBalance extends UserControllerTest {

    String errorTransactionPassword="o campo transactionPassword deve ficar entre 8 e 16 caracteres";
    String url="/Bank/balance/{id}";

    @Test
    @WithMockUser
    public void expectedSuccess_balance() throws Exception {

        DecimalFormat df = new DecimalFormat("###,##0.00");

        BalanceRequestDTO balanceReqDTO=new BalanceRequestDTO()
                .setTransactionPassword("@12345678");

        BalanceResponseDTO balanceRespDTO=  new BalanceResponseDTO()
                .setOperation("Consulta de saldo")
                .setDetail("Saldo")
                .setBalance(df.format(new BigDecimal("1000")));


        Mockito.when(this.walletService.getBalance(any(),any())).thenReturn(balanceRespDTO);
        new EndpointUtilTest().expected_success200_get(mockMvc, url, balanceReqDTO);

        BalanceResponseDTO balanceResponseDTO= this.walletService.getBalance(balanceReqDTO,1L);
//        assertEquals(balanceResponseDTO.getStatus(),200);


    }
    @Test
    @WithMockUser
    public void expected_failure_empty_field_transactionPassword_balance() throws Exception {

        DecimalFormat df = new DecimalFormat("###,##0.00");

        BalanceRequestDTO balanceReqDTO=new BalanceRequestDTO()
                .setTransactionPassword("");

        String error=new EndpointUtilTest().expected_failure400_get(mockMvc, url, balanceReqDTO);
        assertTrue(StringUtils.contains(error,errorTransactionPassword));

    }
    @Test
    @WithMockUser
    public void expected_failure_null_field_transactionPassword_balance() throws Exception {

        DecimalFormat df = new DecimalFormat("###,##0.00");

        BalanceRequestDTO balanceReqDTO=new BalanceRequestDTO();

        String error=new EndpointUtilTest().expected_failure400_get(mockMvc, url, balanceReqDTO);
        assertTrue(StringUtils.contains(error,errorTransactionPassword));

    }
    @Test
    @WithMockUser
    public void expected_failure_invalid_field_transactionPassword_balance() throws Exception {

        DecimalFormat df = new DecimalFormat("###,##0.00");

        BalanceRequestDTO balanceReqDTO=new BalanceRequestDTO()
                .setTransactionPassword("ssd");

        String error=new EndpointUtilTest().expected_failure400_get(mockMvc, url, balanceReqDTO);
        assertTrue(StringUtils.contains(error,errorTransactionPassword));

    }
}
