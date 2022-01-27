package br.com.anderson_silva.Banking_system.dto.request;


import br.com.anderson_silva.Banking_system.customAnnotation.CpfOrCnpj;
import br.com.anderson_silva.Banking_system.customAnnotation.CustomPassword;
import br.com.anderson_silva.Banking_system.customAnnotation.TypeUser;
import br.com.anderson_silva.Banking_system.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "o campo fullName não pode ficar em branco")
    private  String fullName;
    @NotBlank(message = "o campo cpfCnpj não deve ficar em branco")
    @CpfOrCnpj
    private  String cpfCnpj;
    @NotBlank(message = "o campo email não pode ficar em branco ou formato incorreto")
    @Email(message = "o campo email não pode ficar em branco ou formato incorreto")
    private  String email;
    @CustomPassword(message = "o campo password deve ficar entre 8 e 16 caracteres")
    private  String password;
    @TypeUser
    private  String typeUser;

    public User buildUser(){
        User user=new User()
                .setFullName(this.fullName)
                .setCpfCnpj(this.cpfCnpj)
                .setEmail(this.email)
                .setPassword(this.password)
                .setTypeUser(this.typeUser);
        return user;

    }


}
