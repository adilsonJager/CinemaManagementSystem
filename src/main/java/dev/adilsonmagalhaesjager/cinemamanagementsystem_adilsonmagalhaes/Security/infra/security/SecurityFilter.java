package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.infra.security;


import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.repository.UserInternalRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserInternalRepository userInternalRepository;

    public SecurityFilter(TokenService tokenService, UserInternalRepository userInternalRepository) {
        this.tokenService = tokenService;
        this.userInternalRepository = userInternalRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null){
           var subject = tokenService.validateToken(token);
            UserDetails user = userInternalRepository.findByLogin(subject);

            var authenticate = new UsernamePasswordAuthenticationToken( user,null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticate);

        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request ){
        var authHeader = request.getHeader( "Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}
