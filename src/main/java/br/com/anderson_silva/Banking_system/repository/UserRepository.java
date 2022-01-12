package br.com.anderson_silva.Banking_system.repository;

import br.com.anderson_silva.Banking_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByEmail(String email);

    @Query("SELECT u from User u where u.cpf_cnpj =?1")
    public User findUser(@Param("cpf_cnpf")  String cpf_cnpf);

    @Transactional
    @Modifying
    @Query("UPDATE User  u set u.wallet=?2 where u.cpf_cnpj =?1")
    public  int updateBalance(@Param("cpf_cnpf")  String cpf_cnpf,@Param("wallet") BigDecimal wallet);



}
