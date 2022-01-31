package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EndpointTransfer extends UserControllerTest{

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

        this.mockMvc.perform(post("/Bank/transfer")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .content(objectMapper.writeValueAsString(transferReqDTO)))
                .andExpect(status().is(200));

        TransferResponseDTO transferResp= this.walletService.transfer(transferReqDTO);
        assertEquals(transferResp.getStatus(),200);

    }

}
