package br.com.anderson_silva.Banking_system.repositories;

import br.com.anderson_silva.Banking_system.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {


}
