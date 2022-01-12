package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.data.UserDataDetails;
import br.com.anderson_silva.Banking_system.model.User;
import br.com.anderson_silva.Banking_system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Usuário ["+username+"] não encontrado");
        }
        return new UserDataDetails(user);
    }
}
