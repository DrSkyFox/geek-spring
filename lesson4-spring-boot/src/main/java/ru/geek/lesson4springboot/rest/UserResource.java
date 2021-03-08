package ru.geek.lesson4springboot.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.geek.lesson4springboot.controller.BadRequestException;
import ru.geek.lesson4springboot.controller.NotFoundException;
import ru.geek.lesson4springboot.controller.UserController;
import ru.geek.lesson4springboot.service.UserRepr;
import ru.geek.lesson4springboot.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "User resource API", description = "API to manipulate User resource")
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/v1/user")
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    @Autowired
    public UserResource(UserService service) {
        this.service = service;
    }


    @Operation(summary = "Get User List with filter")
    @GetMapping(path = "/filter", produces = "application/json")
    public Page<UserRepr> listPage(
                           @RequestParam("usernameFilter") Optional<String> usernameFilter,
                           @RequestParam("ageMinFilter") Optional<Integer> ageMinFilter,
                           @RequestParam("ageMaxFilter") Optional<Integer> ageMaxFilter,
                           @Parameter(example = "1") @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sortField
    ) {
        logger.info("List page requested: userNameFilter {}, ageMinFilter {}, ageMaxFilter {}, page {}, size {}, sortField {}",
                usernameFilter, ageMinFilter, ageMaxFilter, page, size, sortField);

        return service.findWithFilter(
                usernameFilter.filter(s -> !s.isBlank()).orElse(null),
                ageMinFilter.orElse(null),
                ageMaxFilter.orElse(null),
                page.orElse(1) - 1,
                size.orElse(3),
                sortField.filter(s -> !s.isBlank()).orElse("id")
        );
    }

    @Operation(summary = "Get User List")
    @GetMapping(path = "/all", produces = "application/json")
    public List<UserRepr> findAll() {
        return service.findAll().stream().peek(userRepr -> userRepr.setPassword(null)).collect(Collectors.toList());
    }

    @Operation(summary = "Get User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRepr.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping(path = "/{id}")
    public UserRepr findById(@PathVariable("id") Long id) {
        UserRepr userRepr = service.findById(id)
                .orElseThrow(NotFoundException::new);
        userRepr.setPassword(null);
        return userRepr;
    }

    @Secured("SUPER_ADMIN")
    @Operation(summary = "Create user and save into DB")
    @PostMapping(consumes = "application/json")
    public UserRepr create(@RequestBody UserRepr user) {
        if(user.getId() != null) {
            throw new BadRequestException();
        }
        service.save(user);
        return user;
    }
    @Secured("SUPER_ADMIN")
    @Operation(summary = "Update data of user and save into DB")
    @PutMapping(consumes = "application/json")
    public void update(@RequestBody UserRepr user) {
        if(user.getId() == null) {
            throw new BadRequestException();
        }
        service.save(user);
    }

    @Operation(summary = "Delete User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "ID of user to be deleted") @PathVariable("id") Long id) {
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
