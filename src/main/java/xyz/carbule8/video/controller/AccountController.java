package xyz.carbule8.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.carbule8.video.pojo.Account;
import xyz.carbule8.video.service.AccountService;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public String  login(@Validated @ModelAttribute("account") Account account, BindingResult error, Model model,
                         HttpSession session) {
        if (error.hasErrors()) {
            model.addAttribute("message", error.getFieldError().getDefaultMessage());
            return "login";
        }
        try {
            accountService.findByUsernameAndPassword(account.getUsername(), account.getPassword());
            session.setAttribute("login_session_id", session.getId());
        } catch (AccountNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "login";
        }
        return "redirect:/list";
    }
}
