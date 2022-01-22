package br.com.anderson_silva.Banking_system.repositories;

import br.com.anderson_silva.Banking_system.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
}
