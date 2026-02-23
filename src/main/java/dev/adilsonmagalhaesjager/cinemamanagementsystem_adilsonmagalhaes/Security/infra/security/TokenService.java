package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.model.UserInternal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${JWT_SECRET}")
    private String secret;

    public String generateToken(UserInternal user){

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("CinemaManager")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirateDate())
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw new RuntimeException(e);
        }
    }


    public String validateToken(String token){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("CinemaManager")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private Instant genExpirateDate(){
        return LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant();
    }

}
