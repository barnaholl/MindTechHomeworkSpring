package com.barnaholl.MindTechHomework.controller;

import com.barnaholl.MindTechHomework.dto.MessageDto;
import com.barnaholl.MindTechHomework.dto.PokemonListDto;
import com.barnaholl.MindTechHomework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/pokemon")
    public ResponseEntity<PokemonListDto> findAll(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        return ResponseEntity.ok(userService.findPokemonsByJwtToken(jwtToken, sort));
    }

    @PatchMapping("/pokemon/add")
    public ResponseEntity<MessageDto> addPokemon(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestParam("pokemon") String pokemon) {

        return ResponseEntity.ok(userService.addPokemonByJwtToken(jwtToken, pokemon));
    }

    @PatchMapping("/pokemon/remove")
    public ResponseEntity<MessageDto> removePokemon(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestParam("pokemon") String pokemon) {

        return ResponseEntity.ok(userService.removePokemonByJwtToken(jwtToken, pokemon));
    }
}
