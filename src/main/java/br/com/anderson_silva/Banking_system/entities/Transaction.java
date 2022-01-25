package br.com.anderson_silva.Banking_system.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private  long id;

    @Column(name = "datatime")
    private Date dateTime;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "id_from_wallet")
    private Long idFromWallet;

    @Column(name = "id_to_wallet")
    private  Long  idToWallet;

    @Column(name = "type_transaction")
    private  String typeTransaction;

    @ManyToOne
    @JoinColumn(name = "id_wallet")
    private  Wallet wallet;



}
