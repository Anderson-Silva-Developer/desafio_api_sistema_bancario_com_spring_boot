package br.com.anderson_silva.Banking_system.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseTDO {
    private  String operation;
    private  String status;
    private String detail;
    private  String cpfCnpjDestiny;
    private String AmountDestiny;

    public TransferResponseTDO TransferNotFaund(String amount,String cpfCnpj,String detail){
        this.setOperation("transferência");
        this.setStatus("Falha");
        this.setAmountDestiny(amount);
        this.setCpfCnpjDestiny(cpfCnpj);
        this.setDetail(detail);
        return this;
    }
}
