package uz.guideme.hotelbooking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.guideme.hotelbooking.controller.utils.ResponseUtils;
import uz.guideme.hotelbooking.service.EmailService;
import uz.guideme.hotelbooking.service.dto.EmailDTO;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @PostMapping( "api/hotels/email")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestParam("username") String username,
                                                         @RequestBody EmailDTO emailDTO) {
        log.debug("REST request to send email");
        Optional<Map<String, String>> result = emailService.sendEmail(username, emailDTO.getContent());

        ResponseEntity<Map<String, String>> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

}
