package br.com.anderson_silva.Banking_system.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDTO {
    private  String operation;
    private  String status;
    private String detail;
    private  String cpfCnpjDestiny;
    private String AmountDestiny;
 


}
