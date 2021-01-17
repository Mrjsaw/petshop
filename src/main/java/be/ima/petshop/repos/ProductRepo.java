package be.ima.petshop.repos;


import be.ima.petshop.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends CrudRepository<Product, Integer> {
    List<Product> findAllByCategory(String category);
    List<Product> findAll();
}
