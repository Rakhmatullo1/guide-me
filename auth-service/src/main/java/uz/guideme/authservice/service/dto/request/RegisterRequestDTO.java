package uz.guideme.authservice.service.dto.request;

import lombok.Data;
import uz.guideme.authservice.service.enumeration.UserType;

@Data
public class RegisterRequestDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String email;
    private UserType userType;
}
