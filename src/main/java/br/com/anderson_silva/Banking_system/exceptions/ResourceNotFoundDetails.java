package br.com.anderson_silva.Banking_system.exceptions;

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
public class ResourceNotFoundDetails {
    private int status;
    private String detail;
    private String field;
    private String fieldMessage;

}
