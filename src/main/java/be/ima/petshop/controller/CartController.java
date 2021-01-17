package be.ima.petshop.controller;

import be.ima.petshop.model.Product;
import be.ima.petshop.model.User;
import be.ima.petshop.repos.ProductRepo;
import be.ima.petshop.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @GetMapping(value = "/add")
    public void addProduct(ModelMap map, @RequestParam Integer id, Principal p) {
        User user = userRepo.findByEmail(p.getName());
        Optional<Product> product = productRepo.findById(id);
        product.ifPresent(prod -> user.getCart().add(prod));
        userRepo.save(user);
    }
}
