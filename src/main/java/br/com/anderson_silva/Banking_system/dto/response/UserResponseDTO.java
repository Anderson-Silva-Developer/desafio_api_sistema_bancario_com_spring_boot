package br.com.anderson_silva.Banking_system.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class UserResponseDTO {

    private Long id;
    public UserResponseDTO(Long id){
        this.id=id;

    }

}
