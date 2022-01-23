package br.com.anderson_silva.Banking_system.repositories;

import br.com.anderson_silva.Banking_system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByEmail(String email);
    public Optional<User> findByCpfCnpj(String  cpfCnpj);

    @Transactional
    @Modifying
    @Query("UPDATE Wallet  u set u.balance=?2 where u.id=?1")
    public  int updateBalance(@Param("id") Long id,@Param("balance") BigDecimal balance);


}
