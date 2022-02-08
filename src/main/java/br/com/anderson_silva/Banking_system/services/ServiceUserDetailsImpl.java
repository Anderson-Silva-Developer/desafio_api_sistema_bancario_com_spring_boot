package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.data.UserDataDetails;
import br.com.anderson_silva.Banking_system.entities.User;
import br.com.anderson_silva.Banking_system.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ServiceUserDetailsImpl  implements UserDetailsService {
    private  final UserRepository repository;

    public ServiceUserDetailsImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user=repository.findByEmail(username);
        if(Objects.isNull(user)){

            throw new UsernameNotFoundException(String.format("Usuário %s não encontrado",username));
        }
        return new UserDataDetails(user);
    }
}
