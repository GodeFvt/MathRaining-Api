package sit.project.mathrainingapi.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.project.mathrainingapi.dtos.JwtRequestUser;
import sit.project.mathrainingapi.dtos.JwtTokenResponseDTO;
import sit.project.mathrainingapi.dtos.SingUpRequestDTO;
import sit.project.mathrainingapi.entities.CustomUserDetails;
import sit.project.mathrainingapi.entities.User;
import sit.project.mathrainingapi.exceptions.UnauthorizedException;
import sit.project.mathrainingapi.repositories.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class AuthenticationService {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;


    public JwtTokenResponseDTO login(JwtRequestUser jwtRequestUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtRequestUser.getUserName(), jwtRequestUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Username or Password is incorrect.");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return new JwtTokenResponseDTO(jwtTokenUtil.generateToken(userDetails, false), jwtTokenUtil.generateToken(userDetails, true));
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userOid;
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Refresh Token does not begin with Bearer String or is null");
        } else {
            refreshToken = requestTokenHeader.substring(7);
            try {
                userOid = jwtTokenUtil.getSubjectFromToken(refreshToken, true);
            } catch (IllegalArgumentException e) {
                throw new UnauthorizedException("Unable to get Refresh Token");
            } catch (ExpiredJwtException e) {
                throw new UnauthorizedException("Refresh Token has expired");
            } catch (JwtException e) {
                throw new UnauthorizedException("Refresh Token is invalid");
            }
        }
        if (userOid != null) {
            CustomUserDetails userDetails = jwtUserDetailsService.loadUserByOid(userOid);
            if (jwtTokenUtil.validateToken(refreshToken, userDetails, true)) {
                String accessToken = jwtTokenUtil.generateToken(userDetails, false);
                JwtTokenResponseDTO authResponse = new JwtTokenResponseDTO(accessToken, null);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            } else {
                throw new UnauthorizedException("Invalid refresh token");
            }
        } else {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

    public JwtTokenResponseDTO signup(SingUpRequestDTO singUpRequestDTO) {
        User newUser = new User();
        newUser.setUserName(singUpRequestDTO.getUserName());
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 16, 16);
        newUser.setPassword(argon2.hash(10, 65536, 1, singUpRequestDTO.getPassword()));
        newUser.setEmail(singUpRequestDTO.getEmail());
        newUser.setRole("ROLE_USER");

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return newUser.getRole();
            }
        };
        authorities.add(grantedAuthority);
        CustomUserDetails userDetails = new CustomUserDetails(newUser.getUserName(), newUser.getEmail(), newUser.getPassword(), newUser.getRole(), authorities);

        try {
            userRepository.save(newUser);
        } catch (Exception e) {
            throw new UnauthorizedException("Cannot create user");
        }
        return new JwtTokenResponseDTO(jwtTokenUtil.generateToken(userDetails, false), jwtTokenUtil.generateToken(userDetails, true));
    }


}
