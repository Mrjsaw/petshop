package be.ima.petshop.controller;

import be.ima.petshop.model.User;
import be.ima.petshop.repos.OrderRepo;
import be.ima.petshop.repos.UserDAO;
import be.ima.petshop.repos.UserRepo;
import be.ima.petshop.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MyUserDetails userDetails;
    @Autowired
    private OrderRepo orderRepo;

    @GetMapping(value = "/login")
    public String login(ModelMap map){
        return "login";
    }
    @GetMapping(value = "/register")
    public String register(ModelMap map){
        return "register";
    }
    @PostMapping(value = "/register")
    public String registerNewUser(ModelMap map, @ModelAttribute("user") UserDAO user, BindingResult bindingResult, HttpServletRequest request){
        map.addAttribute("bindingResult", bindingResult);

        if(!user.getPassword().equals(user.getConfirmPassword())){
            bindingResult.rejectValue("password", "");
        }
        if(userRepo.existsByEmail(user.getEmail())){
            bindingResult.rejectValue("email", "");
        }
        if(bindingResult.hasErrors()){
            return "register";
        }
        userDetails.save(user);
        try {
            request.login(user.getEmail(), user.getPassword());
        } catch (ServletException e) {
            return "register";
        }
        return "redirect:/";
    }


    @ModelAttribute(value = "registerUserDAO")
    public UserDAO registerUserDAO()
    {
        return new UserDAO();
    }
}
