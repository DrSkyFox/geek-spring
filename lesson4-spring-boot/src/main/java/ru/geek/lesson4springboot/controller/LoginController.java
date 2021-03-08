package ru.geek.lesson4springboot.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.geek.lesson4springboot.service.UserRepr;
import ru.geek.lesson4springboot.service.UserService;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}
