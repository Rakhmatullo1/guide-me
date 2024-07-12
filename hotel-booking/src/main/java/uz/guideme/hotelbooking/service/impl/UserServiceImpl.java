package uz.guideme.hotelbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.guideme.hotelbooking.service.UserService;
import uz.guideme.hotelbooking.service.client.UserClientService;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserClientService clientService;

    @Override
    public Map<String, String> getUserInfo(String username) {
        log.info("Requested to get user info. ID: {}", username);

        return clientService.fetchUserDataByUsername(username);
    }
}
