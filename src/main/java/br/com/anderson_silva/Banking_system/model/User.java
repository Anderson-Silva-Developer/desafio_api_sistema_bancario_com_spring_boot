package br.com.anderson_silva.Banking_system.model;

import br.com.anderson_silva.Banking_system.customAnnotation.CpfOrCnpj;
import br.com.anderson_silva.Banking_system.customAnnotation.TypeUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private BigDecimal wallet=new BigDecimal("1000");


    @NotEmpty(message = "campo full_name inválido  não pode ser vazio")
    private  String full_name;

    @NotNull
    @Column(unique = true)
    @CpfOrCnpj
    private  String cpf_cnpj;

    @NotNull
    @Column(unique = true)
    @NotEmpty(message = "campo email inválido  formato aceito ex:xxxxx@... ")
    @Email(message = "campo email inválido  formato aceito ex:xxxxx@... ")
    private  String email;


    @Size( min = 8,message = "campo password inválido  não pode ser menor que 8 caracteres ou vazio")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;


    @Size( min = 8,message = "campo transaction_password inválido  não pode ser menor que 8 caracteres ou vazio")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String transaction_password;


    @TypeUser
    private  String type_user;


}

