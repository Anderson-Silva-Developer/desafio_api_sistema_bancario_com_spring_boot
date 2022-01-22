package br.com.anderson_silva.Banking_system.dto.request;


import br.com.anderson_silva.Banking_system.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private  String fullName;
    private  String cpfCnpj;
    private  String email;
    private  String password;
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
