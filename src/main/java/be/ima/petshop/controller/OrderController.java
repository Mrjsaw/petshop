package be.ima.petshop.controller;

import be.ima.petshop.model.Order;
import be.ima.petshop.model.User;
import be.ima.petshop.repos.OrderRepo;
import be.ima.petshop.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping(value = "/orders")
    public String orders(ModelMap map, Principal principal){
        User user = userRepo.findByEmail(principal.getName());
        map.addAttribute("orders", user.getOrders());
        return "orders";
    }
}
