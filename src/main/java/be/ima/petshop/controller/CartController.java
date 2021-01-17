package be.ima.petshop.controller;

import be.ima.petshop.model.Order;
import be.ima.petshop.model.Product;
import be.ima.petshop.model.User;
import be.ima.petshop.repos.OrderRepo;
import be.ima.petshop.repos.ProductRepo;
import be.ima.petshop.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;

    @GetMapping(value = "/cart/add")
    public RedirectView addProduct(ModelMap map, @RequestParam Integer id, Principal p) {
        User user = userRepo.findByEmail(p.getName());
        Optional<Product> product = productRepo.findById(id);
        product.ifPresent(prod -> user.getCart().add(prod));
        userRepo.save(user);
        return new RedirectView("/index");
    }


    @GetMapping(value = "/cart/show")
    public String showCart(ModelMap map, Principal p) {
        User user = userRepo.findByEmail(p.getName());

        map.addAttribute("products_cart", user.getCart());
        int sum = 0;
        for (Product x : user.getCart()) {
            sum += x.getPrice();
        }
        map.addAttribute("total_price",sum);
        return "cart";
    }
    @PostMapping(value = "/cart")
    public RedirectView payCart(ModelMap map, Principal p) {
        User user = userRepo.findByEmail(p.getName());
        //make a copy so you don't have shared references to a collection
        List<Product> myCart = new ArrayList<>(user.getCart());
        double sum = 0.0;
        for (Product x : myCart) {
            sum += x.getPrice();
        }
        Order order = new Order(myCart,user,sum);

        orderRepo.save(order);
        user.getOrders().add(order);
        user.getCart().clear();
        userRepo.save(user);
        return new RedirectView("/orders");
    }
}
