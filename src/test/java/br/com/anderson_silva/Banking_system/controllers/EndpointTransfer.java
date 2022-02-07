package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.controllers.util.EndpointUtilTest;
import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

public class EndpointTransfer extends UserControllerTest {

    String errorAmountDestiny="O campo amountDestiny apresenta formato inválido  use ex:[1.0],[1.000],[0,10],[1.100,00] e maior que 0";
    String errorCpfCnpjDestiny ="campo cpfCnpj inválido formato aceito ex:cpf[xxx.xxx.xxx-xx] ou cnpj[xxx.xxx.xxx-xx]";
    String errorTransactionPassword="o campo transactionPassword deve ficar entre 8 e 16 caracteres";
    String url="/Bank/clients/transfer";

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

        new EndpointUtilTest().expected_success200_post(mockMvc, url,transferReqDTO);

        TransferResponseDTO transferResp= this.walletService.transfer(transferReqDTO);
        assertEquals(transferResp.getStatus(),200);

    }
    @Test
    @WithMockUser
    public void expected_failure_empty_field_amountDestiny_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
                .setAmountDestiny("")
                .setCpfCnpjDestiny("957.051.460-43")
                .setTransactionPassword("@12345678");

        String error =new EndpointUtilTest().expected_failure400_post(mockMvc, url,transferReqDTO);
        assertTrue(StringUtils.contains(error,errorAmountDestiny ));

    }
    @Test
    @WithMockUser
    public void expected_failure_null_field_amountDestiny_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
                .setCpfCnpjDestiny("957.051.460-43")
                .setTransactionPassword("@12345678");

        String error =new EndpointUtilTest().expected_failure400_post(mockMvc, url,transferReqDTO);
        assertTrue(StringUtils.contains(error,errorAmountDestiny ));

    }
    @Test
    @WithMockUser
    public void expected_failure_invalid_field_amountDestiny_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
                .setAmountDestiny("!((")
                .setCpfCnpjDestiny("957.051.460-43")
                .setTransactionPassword("@12345678");

        String error =new EndpointUtilTest().expected_failure400_post(mockMvc, url,transferReqDTO);
        assertTrue(StringUtils.contains(error,errorAmountDestiny ));

    }
    @Test
    @WithMockUser
    public void expected_failure_empty_field_CpfCnpj_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
                .setAmountDestiny("10")
                .setCpfCnpjDestiny("")
                .setTransactionPassword("@12345678");

        String error =new EndpointUtilTest().expected_failure400_post(mockMvc, url,transferReqDTO);
        assertTrue(StringUtils.contains(error, errorCpfCnpjDestiny));

    }
    @Test
    @WithMockUser
    public void expected_failure_null_field_CpfCnpj_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
                .setAmountDestiny("10")
                .setTransactionPassword("@12345678");

        String error =new EndpointUtilTest().expected_failure400_post(mockMvc, url,transferReqDTO);
        assertTrue(StringUtils.contains(error, errorCpfCnpjDestiny));

    }
    @Test
    @WithMockUser
    public void expected_failure_invalid_field_CpfCnpj_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
                .setAmountDestiny("10")
                .setCpfCnpjDestiny("545-45-45-454")
                .setTransactionPassword("@12345678");

        String error =new EndpointUtilTest().expected_failure400_post(mockMvc, url,transferReqDTO);
        assertTrue(StringUtils.contains(error, errorCpfCnpjDestiny));

    }
    @Test
    @WithMockUser
    public void expected_failure_empty_field_transactionPassword_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
                .setAmountDestiny("10")
                .setCpfCnpjDestiny("957.051.460-43")
                .setTransactionPassword("");

        String error =new EndpointUtilTest().expected_failure400_post(mockMvc, url,transferReqDTO);
        assertTrue(StringUtils.contains(error,errorTransactionPassword));

    }
    @Test
    @WithMockUser
    public void expected_failure_null_field_transactionPassword_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
                .setAmountDestiny("10")
                .setCpfCnpjDestiny("957.051.460-43");


        String error =new EndpointUtilTest().expected_failure400_post(mockMvc, url,transferReqDTO);
        assertTrue(StringUtils.contains(error,errorTransactionPassword));

    }
    @Test
    @WithMockUser
    public void expected_failure_invalid_field_transactionPassword_transfer() throws Exception {
        TransferRequestDTO transferReqDTO=new TransferRequestDTO()
                .setAmountDestiny("10")
                .setCpfCnpjDestiny("957.051.460-43")
                .setTransactionPassword("dsd");

        String error =new EndpointUtilTest().expected_failure400_post(mockMvc, url,transferReqDTO);
        assertTrue(StringUtils.contains(error,errorTransactionPassword));

    }

}
