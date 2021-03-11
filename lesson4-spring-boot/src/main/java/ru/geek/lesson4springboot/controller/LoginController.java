package ru.geek.lesson4springboot.controller;

import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geek.lesson4springboot.repositories.RoleRepository;
import ru.geek.lesson4springboot.service.UserRepr;
import ru.geek.lesson4springboot.service.UserService;

import javax.validation.Valid;

@Controller
public class LoginController {


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;
    private final RoleRepository roleRepository;


    @Autowired
    public LoginController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login_form";
    }

    @GetMapping("/reg")
    public String registrationPage(Model model) {
        model.addAttribute("user", new UserRepr());
        return "reg_form";
    }

    @PostMapping("/reguser")
    public String update(@Valid @ModelAttribute("user") UserRepr user, BindingResult result, Model model) {
        logger.info("Update endpoint requested");

        model.addAttribute("roles", roleRepository.findAll());
        if(result.hasErrors()) {
            logger.info("Some errors");
            return "reg_form";
        }

        if(!user.getPassword().equals(user.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            logger.info("Password not matching");
            return "reg_form";
        }
        logger.info("Updating user with id {}", user.getId());
        userService.save(user);

        return "redirect:/login";
    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}
