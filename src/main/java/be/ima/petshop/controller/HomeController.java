package be.ima.petshop.controller;

import be.ima.petshop.model.Product;
import be.ima.petshop.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductRepo productRepo;
    @RequestMapping(value = {"/", "/index"} , method = RequestMethod.GET)
    public String home(ModelMap model){
        List<Product> allProducts = productRepo.findAll();
        model.addAttribute("producten", allProducts);
        return "index";
    }
}
