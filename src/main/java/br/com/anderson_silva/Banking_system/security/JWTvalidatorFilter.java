package br.com.anderson_silva.Banking_system.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTvalidatorFilter extends BasicAuthenticationFilter {
    public static final String  ATTRIBUTE_HEADER="Authorization";
    public static final String  ATTRIBUTE_PREFIX="Bearer ";



    public JWTvalidatorFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String attribute=request.getHeader(ATTRIBUTE_HEADER);
        if(attribute==null){
            chain.doFilter(request,response);
            return;

        }
        if(!attribute.startsWith(ATTRIBUTE_PREFIX)){
            chain.doFilter(request,response);
            return;

        }
        String token=attribute.replace(ATTRIBUTE_PREFIX,"");
        UsernamePasswordAuthenticationToken authenticationToken=getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);

    }
    private UsernamePasswordAuthenticationToken getAuthenticationToken( String token){
        String user = JWT.require(Algorithm.HMAC512(JWTauthenticateFilter.TOKEN_PASSWORD))
                .build()
                .verify(token)
                .getSubject();
        if(user==null){
            return null;

        }
        return  new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
    }
}
