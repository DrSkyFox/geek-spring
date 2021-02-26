package ru.geek.lesson4springboot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geek.lesson4springboot.controller.BadRequestException;
import ru.geek.lesson4springboot.controller.NotFoundException;
import ru.geek.lesson4springboot.persist.User;
import ru.geek.lesson4springboot.service.UserRepr;
import ru.geek.lesson4springboot.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource {
    private final UserService service;

    @Autowired
    public UserResource(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<UserRepr> findAll() {
        return service.findAll().stream().peek(userRepr -> userRepr.setPassword(null)).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public UserRepr findById(@PathVariable("id") Long id) {
        UserRepr userRepr = service.findById(id)
                .orElseThrow(NotFoundException::new);
        userRepr.setPassword(null);
        return userRepr;
    }

    @PostMapping(consumes = "application/json")
    public UserRepr create(@RequestBody UserRepr user) {
        if(user.getId() != null) {
            throw new BadRequestException();
        }
        service.save(user);
        return user;
    }

    @PutMapping(consumes = "application/json")
    public void update(@RequestBody UserRepr user) {
        if(user.getId() == null) {
            throw new BadRequestException();
        }
        service.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> badRequestException(BadRequestException ex) {
        return new ResponseEntity<>("Bad request exception", HttpStatus.NOT_FOUND);
    }

}
