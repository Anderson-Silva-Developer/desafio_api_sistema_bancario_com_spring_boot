package br.com.anderson_silva.Banking_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private  String full_name;
    @Column(unique = true)
    private  String cpf_cnpj;
    @Column(unique = true)
    private  String email;
    private  String password;
    private  String type_user="";

}
