package br.com.anderson_silva.Banking_system.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private  long id;

    @Column(name = "balance")
    private BigDecimal balance=new BigDecimal("1000");

    @Column(name = "password_transaction")
    private String passwordTransaction;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "wallet")
    private  User user;

    @OneToMany(mappedBy = "wallet")
    private  List<Transaction> transaction;


}
