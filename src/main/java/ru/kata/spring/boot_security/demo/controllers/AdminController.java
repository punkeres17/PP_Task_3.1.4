package ru.kata.spring.boot_security.demo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.ResponseError;
import ru.kata.spring.boot_security.demo.util.UserNotAddedAndNotUpdatedException;
import ru.kata.spring.boot_security.demo.util.UserNotfoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(final UserService userService, final ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> showAllUsers() {
        return new ResponseEntity<>(userService
                .getAllUsers()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> showUserById(@PathVariable(name = "id") final Long id) {
        final User user = userService.getUserById(id);
        return new ResponseEntity<>(convertToUserDTO(user), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<HttpStatus> addUser(
            @RequestBody @Valid final UserDTO userDTO,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            final List<FieldError> errors = bindingResult.getFieldErrors();
            final String errorMessage = errors
                    .stream()
                    .map(error -> error.getField() + " - " + error.getDefaultMessage() + ";")
                    .collect(Collectors.joining());
            throw new UserNotAddedAndNotUpdatedException(errorMessage);
        }
        userService.saveUser(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody @Valid final UserDTO userDTO,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            final List<FieldError> errors = bindingResult.getFieldErrors();
            final String errorMessage = errors
                    .stream()
                    .map(error -> error.getField() + " - " + error.getDefaultMessage() + ";")
                    .collect(Collectors.joining());
            throw new UserNotAddedAndNotUpdatedException(errorMessage);
        }
        final User userUpdate = userService.updateUser(convertToUser(userDTO));
        return new ResponseEntity<>(convertToUserDTO(userUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable(name = "id") final Long id) {
        userService.removeUserById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UserNotAddedAndNotUpdatedException.class)
    public ResponseEntity<ResponseError> handlerErrors(final UserNotAddedAndNotUpdatedException userNotAddedAndNotUpdatedException) {
        final ResponseError responseError = new ResponseError(
                userNotAddedAndNotUpdatedException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotfoundException.class)
    public ResponseEntity<ResponseError> handlerErrors(final UserNotfoundException userNotfoundException) {
        final ResponseError responseError = new ResponseError(
                "User with this ID not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    private User convertToUser(final UserDTO userDTO) {

        return modelMapper.map(userDTO, User.class);

    }

    private UserDTO convertToUserDTO(final User user) {
        return modelMapper.map(user, UserDTO.class);
    }


}
