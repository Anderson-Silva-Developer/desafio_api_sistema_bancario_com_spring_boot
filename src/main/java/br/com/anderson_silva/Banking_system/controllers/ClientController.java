package br.com.anderson_silva.Banking_system.controllers;

import br.com.anderson_silva.Banking_system.dto.request.UserRequestDTO;
import br.com.anderson_silva.Banking_system.dto.response.UserResponseDTO;
import br.com.anderson_silva.Banking_system.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class ClientController {

    private final UserService userService;

    public ClientController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/clients")
    public ResponseEntity<UserResponseDTO> save(@RequestBody @Valid UserRequestDTO userDTO) {

        UserResponseDTO userResponseDTO = this.userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);

    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UserRequestDTO userDTO, @PathVariable("id") Long id) {

        UserResponseDTO userResponseDTO = this.userService.update(userDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);

    }

}
