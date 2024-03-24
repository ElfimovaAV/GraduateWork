package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.backend.dto.AuthenticationRequest;
import ru.gb.backend.dto.AuthenticationResponse;
import ru.gb.backend.dto.RegisterRequest;
import ru.gb.backend.models.User;
import ru.gb.backend.repositories.UserRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    @Autowired
    private IJwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        System.out.println(user);
        //!!! issue here
        userRepository.save(user);
        var jwtToken =jwtService.generateToken(user);
        log.info("IN register - user: {} registered", user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),request.getPassword()
        ));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken =jwtService.generateToken(user);
        log.info("IN authenticate - user: {} authenticated", user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
