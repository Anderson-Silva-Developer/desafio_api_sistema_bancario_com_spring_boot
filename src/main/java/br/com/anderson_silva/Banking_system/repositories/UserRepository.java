package br.com.anderson_silva.Banking_system.repositories;

import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByWallet(Wallet wallet);
    public Optional<User> findById(Long id);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByCpfCnpj(String  cpfCnpj);

}
