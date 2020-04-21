package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.UserPrinciple;
import edu.fondue.electronicdocuments.configuration.security.JwtProvider;
import edu.fondue.electronicdocuments.dto.JwtResponseDto;
import edu.fondue.electronicdocuments.dto.SignInDto;
import edu.fondue.electronicdocuments.dto.SignUpDto;
import edu.fondue.electronicdocuments.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final UserService userService;

    private final PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> authenticate(final SignInDto request) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(JwtResponseDto.builder()
                .id(((UserPrinciple)authentication.getPrincipal()).getId())
                .token(jwt)
                .username(request.getUsername()).build());
    }

    @Override
    public ResponseEntity<String> signup(final SignUpDto request) {
        if(userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Fail -> Username is already taken!");
        }

        if(userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Fail -> Email is already in use!");
        }

        final User user = User.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .roles(new HashSet<>()).build();

        userService.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }
}