package br.com.anderson_silva.Banking_system.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    public UserResponseDTO(Long id){
        this.id=id;

    }

}
