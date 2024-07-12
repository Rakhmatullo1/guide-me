package uz.guideme.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uz.guideme.userservice.model.UserModel;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel, String> {

    Optional<UserModel> findByUsername(String username);

}
