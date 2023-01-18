package com.barnaholl.MindTechHomework.controller;

import com.barnaholl.MindTechHomework.dto.JwtTokenDto;
import com.barnaholl.MindTechHomework.dto.UserDto;
import com.barnaholl.MindTechHomework.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseEntity<JwtTokenDto> register(@RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(authenticationService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.login(userDto));
    }
}
