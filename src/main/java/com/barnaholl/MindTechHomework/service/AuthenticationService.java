package com.barnaholl.MindTechHomework.service;

import com.barnaholl.MindTechHomework.security.JwtService;
import com.barnaholl.MindTechHomework.dto.JwtTokenDto;
import com.barnaholl.MindTechHomework.dto.UserDto;
import com.barnaholl.MindTechHomework.model.User;
import com.barnaholl.MindTechHomework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtTokenDto register(UserDto userDto) throws Exception {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .pokemons(Set.of())
                .build();

       String jwtToken = "Username is already taken";

        if(userRepository.findUserByUsername(user.getUsername()).isEmpty()){
            userRepository.save(user);
             jwtToken = jwtService.generateToken(user);
        }


       return JwtTokenDto.builder().token(jwtToken).build();

    }

    public JwtTokenDto login(UserDto userDto) throws UsernameNotFoundException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getUsername(),
                            userDto.getPassword()
                    ));
        } catch (Exception e) {
            throw new AuthenticationServiceException("Authentication exception");
        }

        User user = userRepository
                .findUserByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        String jwtToken = jwtService.generateToken(user);
        return JwtTokenDto.builder().token(jwtToken).build();

    }
}
