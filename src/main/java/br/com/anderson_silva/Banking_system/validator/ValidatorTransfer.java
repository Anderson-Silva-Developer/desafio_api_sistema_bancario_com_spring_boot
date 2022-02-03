package br.com.anderson_silva.Banking_system.validator;


import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
public class ValidatorTransfer {
    //verifications
    public TransferResponseDTO validatorRequestTransfer(Authentication auth, TransferRequestDTO transferReqTDO, User userOrigin, User userDestiny, boolean isPassword, BigDecimal balance){

        if(!auth.isAuthenticated()){
            return  new TransferResponseDTO()
                    .setOperation("transferência")
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setAmountDestiny(transferReqTDO.getAmountDestiny())
                    .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                    .setDetail("usuário não autenticado");
        }


        if(balance.compareTo(new BigDecimal("0.0"))==0){
            return  new TransferResponseDTO()
                    .setOperation("transferência")
                    .setStatus(HttpStatus.BAD_REQUEST.value())
                    .setAmountDestiny(transferReqTDO.getAmountDestiny())
                    .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                    .setDetail("saldo insuficiente");

        }

        if(!isPassword){
            return  new TransferResponseDTO()
                    .setOperation("transferência")
                    .setStatus(HttpStatus.BAD_REQUEST.value())
                    .setAmountDestiny(transferReqTDO.getAmountDestiny())
                    .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                    .setDetail("usuário ou senha de transação incorreto");

        }

        if(Objects.isNull(userDestiny)){
            return  new TransferResponseDTO()
                    .setOperation("transferência")
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setAmountDestiny(transferReqTDO.getAmountDestiny())
                    .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                    .setDetail("cpfCnpjDestiny não encontrado");
        }


        if(Objects.isNull(userOrigin) || userOrigin.getTypeUser().equals("shopkeeper")){
            return  new TransferResponseDTO()
                    .setOperation("transferência")
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setAmountDestiny(transferReqTDO.getAmountDestiny())
                    .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                    .setDetail("usuário não autorizado");

        }


        return null;
    }
}
