package ru.gb.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.backend.dto.AuthenticationRequest;
import ru.gb.backend.dto.AuthenticationResponse;
import ru.gb.backend.dto.RegisterRequest;
import ru.gb.backend.services.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Перехват команды на регистрацию нового пользователя
     * @param request
     * @return token и код ответа 200
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register
    (@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    /**
     * Перехват команды на аутентификацию зарегистрированного пользователя
     * @param request
     * @return token и код ответа 200
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate
    (@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
