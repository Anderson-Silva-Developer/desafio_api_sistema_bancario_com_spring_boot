package br.com.anderson_silva.Banking_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferStatus implements Serializable {
    private  String titulo;
    private  String status;

    public TransferStatus Recuse(){
        this.setTitulo("Transação Bancaria de transferência");
        this.setStatus("Operação Recusada!");
        return this;
    }
    public TransferStatus Accept(){
        this.setTitulo("Transação Bancaria de transferência");
        this.setStatus("Operação Aceita!");
        return this;
    }


}
