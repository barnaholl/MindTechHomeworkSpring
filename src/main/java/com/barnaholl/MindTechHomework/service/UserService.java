package com.barnaholl.MindTechHomework.service;

import com.barnaholl.MindTechHomework.security.JwtService;
import com.barnaholl.MindTechHomework.dto.MessageDto;
import com.barnaholl.MindTechHomework.dto.PokemonListDto;
import com.barnaholl.MindTechHomework.model.User;
import com.barnaholl.MindTechHomework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private User getUserByJwtToken(String jwtToken) {
        String username = jwtService.extractUsername(jwtToken.substring("Bearer ".length()));

        return userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with this username in the database"));

    }

    public MessageDto addPokemonByJwtToken(String jwtToken, String pokemon) {

        User user = getUserByJwtToken(jwtToken);

        Set<String> pokemonList = user.getPokemons();
        String message = "Pokemon is already in the list of " + user.getUsername();

        if (!pokemonList.contains(pokemon)) {
            pokemonList.add(pokemon);
            user.setPokemons(pokemonList);
            userRepository.save(user);
            message = "Pokemon: " + pokemon + " has been added to pokemon list of user: " + user.getUsername();
        }

        return MessageDto
                .builder()
                .message(message)
                .build();
    }

    public MessageDto removePokemonByJwtToken(String jwtToken, String pokemon) {

        User user = getUserByJwtToken(jwtToken);
        Set<String> pokemonList = user.getPokemons();
        String message = "Pokemon is not in the list of " + user.getUsername();

        if (pokemonList.contains(pokemon)) {
            pokemonList.remove(pokemon);
            user.setPokemons(pokemonList);
            userRepository.save(user);
            message = "Pokemon: " + pokemon + " has been removed form pokemon list of user: " + user.getUsername();
        }

        return MessageDto
                .builder()
                .message(message)
                .build();
    }

    public PokemonListDto findPokemonsByJwtToken(String jwtToken, String sort) {
        User user = getUserByJwtToken(jwtToken);

        List<String> pokemonList =
                userRepository
                        .findUserByUsername(user.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found"))
                        .getPokemons().stream().toList();
        if (sort != null) {
            switch (sort) {
                case "ASC" -> pokemonList = pokemonList.stream().sorted(Comparator.naturalOrder()).toList();
                case "DESC" -> pokemonList = pokemonList.stream().sorted(Comparator.reverseOrder()).toList();
            }
        }

        return PokemonListDto.builder().pokemonList(pokemonList).build();
    }
}
