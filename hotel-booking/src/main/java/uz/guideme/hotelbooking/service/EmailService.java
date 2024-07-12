package uz.guideme.hotelbooking.service;

import java.util.Map;
import java.util.Optional;

public interface EmailService {

    Optional<Map<String, String>> sendEmail(String email, String text);

}
