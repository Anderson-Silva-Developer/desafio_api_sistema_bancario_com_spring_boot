package br.com.anderson_silva.Banking_system.security;

import br.com.anderson_silva.Banking_system.data.UserDataDetails;
import br.com.anderson_silva.Banking_system.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTauthenticateFilter  extends UsernamePasswordAuthenticationFilter {
    public static final int TOKEN_EXPIRATION=600_000;
    public static final String  TOKEN_PASSWORD="0dc40fda-2903-4607-8d0a-850da4be3048";
    private final AuthenticationManager authenticationManager;

    public JWTauthenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDataDetails userDataDetails=( UserDataDetails)authResult.getPrincipal();
        String token= JWT.create()
                .withSubject(userDataDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));
        response.getWriter().write(token);
        response.getWriter().flush();


    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user=new ObjectMapper().readValue(request.getInputStream(),User.class);

            return  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuario",e);
        }

    }
}
