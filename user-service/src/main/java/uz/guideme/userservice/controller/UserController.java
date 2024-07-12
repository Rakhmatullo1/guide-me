package uz.guideme.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uz.guideme.userservice.model.UserModel;
import uz.guideme.userservice.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserModel> getUserModel(@PathVariable("id") String username) {
        return ResponseEntity.ok(userService.getUserModel(username));
    }

}
