package com.example.weekendproject.controller;

import com.example.weekendproject.model.Token;
import com.example.weekendproject.model.User;
import com.example.weekendproject.service.TokenService;
import com.example.weekendproject.service.UserService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
  final UserService userService;
  final TokenService tokenService;

  public LoginController(UserService userService,
      TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @RequestMapping("/login")
  public String loginPage() {
    return "login";
  }

  @RequestMapping(value = "/registration", method = RequestMethod.GET)
  public String showSignUpForm(Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "registration";
  }

  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public String signUp(@Valid User user, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "registration";
    } else if (userService.isUserAlreadyInBase(user)) {
      bindingResult.rejectValue("username", "error.user",
          "There is already a user registered with the email or username provided");
    } else {
      userService.addUser(user);
      model.addAttribute("message", "Link with activation was sent to your email.");
    }
    return "messagePage";
  }

  @RequestMapping(value = "/activation/{id}", method = RequestMethod.GET)
  public String activateUser(@PathVariable String id, Model model) {
    Optional<Token> token = tokenService.findTokenById(id);
    String message = "";
    if (!userService.isAnon()) {
      message = "Activation is not available, please log out first.";
    } else if (token.isEmpty()) {
      message = "There is no such token.";
    } else if (tokenService.isExpired(token.get())) {
      message = "Token is expired. We sent you email with another one.";
      userService.reSendTokenEmail(id);
    } else {
      User user = userService.findByToken(token.get());
      message =
          user.isActive() ? "Your account is already active" : "You have activated your account.";
      userService.activateUser(user);
    }
    model.addAttribute("message", message);
    return "messagePage";
  }
}
