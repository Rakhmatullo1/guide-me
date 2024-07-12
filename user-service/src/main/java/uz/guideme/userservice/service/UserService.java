package uz.guideme.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import uz.guideme.userservice.model.UserModel;
import uz.guideme.userservice.repository.UserRepository;

@Service
@Slf4j
public class UserService {

    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;

    public UserService(MongoTemplate mongoTemplate, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }

    public UserModel getUserModel(String username) {
        UserModel userModel = userRepository.findByUsername(username).orElse(new UserModel(1, "demo", "demo"));

        log.info("User: {}", userModel);
        return userModel;

    }
}
