package br.com.anderson_silva.Banking_system.model;

import br.com.anderson_silva.Banking_system.customAnnotation.CpfOrCnpj;
import br.com.anderson_silva.Banking_system.customAnnotation.TypeUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private BigDecimal wallet=new BigDecimal("1000");

    @NotNull(message = "O campo full_name deve ser preenchido")
    @Size(min = 3,max = 255)
    private  String full_name;

    @Column(unique = true)
    @CpfOrCnpj
    private  String cpf_cnpj;

    @Email
    @Column(unique = true)
    private  String email;


    @NotNull(message = "O campo password deve ser preenchido")
    @Size(min = 8,max = 16)
    private  String password;

    @TypeUser
    private  String type_user;


}

