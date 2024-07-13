package uz.guideme.bazaar.service.impl.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import uz.guideme.bazaar.service.exception.CustomException;
import uz.guideme.bazaar.service.exception.InvalidArgumentException;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
public class TokenUtils {

    public static String getUsername(String token) {

        if(Objects.isNull(token)) {
            throw new InvalidArgumentException("Token cannot be null, Send it in headers");
        }

        ObjectMapper mapper = new ObjectMapper();
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String[] parts = token.split("\\.");
        String payload = parts[1];
        payload = new String(decoder.decode(payload.getBytes()));

        try {
            return mapper.readValue(payload.getBytes(), Map.class).get("username").toString();
        } catch (IOException e) {
            throw new CustomException(e.getLocalizedMessage());
        }
    }

}
