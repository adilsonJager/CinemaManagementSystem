package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.infra.security.TokenService;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.model.UserInternal;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.model.UserRole;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.model.dto.AuthenticationDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.model.dto.RegisterDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.repository.UserInternalRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserInternalRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;



    public AuthenticationController(AuthenticationManager authenticationManager, UserInternalRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto request){

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserInternal) auth.getPrincipal());

        return ResponseEntity.ok(token);

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserInternal newUser = new UserInternal(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();

    }

}
