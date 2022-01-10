package br.com.anderson_silva.Banking_system.validator;

import br.com.anderson_silva.Banking_system.model.User;
import br.com.anderson_silva.Banking_system.repository.UserRepository;
import br.com.anderson_silva.Banking_system.util.BeanUtil;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidatorUser {
    private  UserRepository myRepo = BeanUtil.getBean(UserRepository.class);
    public  User validate_user(String cpf_cnpj,String password){

       try {
               User user = !password.equals("")?myRepo.findUserOrigin(cpf_cnpj, password):myRepo.findUserDestiny(cpf_cnpj);
               if (user != null) {
                   return user;
               }

       }catch (Exception e){
           System.out.println(e.getMessage());
       }

        return null;
    }
}
