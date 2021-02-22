package ru.geek.lesson4springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geek.lesson4springboot.service.UserRepr;
import ru.geek.lesson4springboot.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listPage(Model model) {
        logger.info("List page requested");
        model.addAttribute("users", userService.findAll());
        return "user";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        logger.info("Edit page for id {} requested", id);
        model.addAttribute("user", userService.findById(id).orElseThrow(NotFoundException::new));
        return "user_form";
    }

    @PostMapping("/update")
    public String update(@Valid UserRepr user, BindingResult result) {
        logger.info("Update endpoint requested");
        //метод возвращающий ошибки, если есть
        if(result.hasErrors()) {
            logger.info("Some errors");
            return "user_form";
        }

        if(!user.getPassword().equals(user.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            logger.info("Password not matching");
            return "user_form";
        }
        logger.info("Updating user with id {}", user.getId());
        userService.save(user);

        return "redirect:/user";
    }

    @GetMapping("/new")
    public String create(Model model)
    {
        model.addAttribute("user", new UserRepr());
        return "user_form";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") Long id) {
        logger.info("User delete request");
        userService.delete(id);
        return "redirect:/user";
    }
}
