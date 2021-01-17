package be.ima.petshop.repos;

import be.ima.petshop.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
