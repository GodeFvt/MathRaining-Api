package sit.project.mathrainingapi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sit.project.mathrainingapi.dtos.JwtRequestUser;
import sit.project.mathrainingapi.dtos.JwtTokenResponseDTO;
import sit.project.mathrainingapi.dtos.SingUpRequestDTO;
import sit.project.mathrainingapi.services.AuthenticationService;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:5173/","https://phuttinanphak.com/"})
@RestController
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponseDTO> login(@RequestBody @Valid JwtRequestUser jwtRequestUser) {
        return ResponseEntity.ok(authenticationService.login(jwtRequestUser));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtTokenResponseDTO> signup(@RequestBody @Valid SingUpRequestDTO singUpRequestDTO) {
        return ResponseEntity.ok(authenticationService.signup(singUpRequestDTO));
    }

    @PostMapping("/token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }


}
