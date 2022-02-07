package br.com.anderson_silva.Banking_system.validator;


import br.com.anderson_silva.Banking_system.dto.request.TransferRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.TransferResponseDTO;
import br.com.anderson_silva.Banking_system.entities.User;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

@NoArgsConstructor
public class ValidatorTransfer {

    public TransferResponseDTO validatorRequestTransfer(Authentication auth, TransferRequestDTO transferReqTDO, User userOrigin, User userDestiny) {


        if (userOrigin == userDestiny) {
            return new TransferResponseDTO()
                    .setOperation("transferência")
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setAmountDestiny(transferReqTDO.getAmountDestiny())
                    .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                    .setDetail("usuário destino não pode ser igual ao de origem");

        }


        if (!auth.isAuthenticated()) {
            return new TransferResponseDTO()
                    .setOperation("transferência")
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setAmountDestiny(transferReqTDO.getAmountDestiny())
                    .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                    .setDetail("usuário não autenticado");
        }

        if (userOrigin.getTypeUser().equals("shopkeeper")) {
            return new TransferResponseDTO()
                    .setOperation("transferência")
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setAmountDestiny(transferReqTDO.getAmountDestiny())
                    .setCpfCnpjDestiny(transferReqTDO.getCpfCnpjDestiny())
                    .setDetail("usuário não autorizado");

        }


        return null;
    }
}
