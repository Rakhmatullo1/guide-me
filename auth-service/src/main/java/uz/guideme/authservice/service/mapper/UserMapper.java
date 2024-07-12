package uz.guideme.authservice.service.mapper;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import uz.guideme.authservice.service.dto.request.RegisterRequestDTO;
import uz.guideme.authservice.service.enumeration.UserType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapper {

    private final static Map<UserType, String> GROUPS = new HashMap<>();
    private final static Map<UserType, String> ROLES = new HashMap<>();
    private final static String PHONE_NUMBER_KEY = "phoneNumber";

    static {
        GROUPS.put(UserType.HOTEL_OWNER, "Hotel_Owners");
        GROUPS.put(UserType.SELLER, "Sellers");

        ROLES.put(UserType.SELLER, "ROLE_SELLER");
        ROLES.put(UserType.HOTEL_OWNER, "ROLE_HOTEL_OWNER");
    }

    public static UserRepresentation toUserRepresentation(RegisterRequestDTO requestDTO) {
        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setEmail(requestDTO.getEmail());
        userRepresentation.setAttributes(Map.of(PHONE_NUMBER_KEY, List.of(requestDTO.getPhoneNumber())));
        userRepresentation.setUsername(requestDTO.getEmail());
        userRepresentation.setFirstName(requestDTO.getFirstName());
        userRepresentation.setLastName(requestDTO.getLastName());
        userRepresentation.setCredentials(createCredentials(requestDTO.getPassword()));
        userRepresentation.setGroups(List.of(GROUPS.get(requestDTO.getUserType())));
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        userRepresentation.setRealmRoles(List.of(ROLES.get(requestDTO.getUserType())));

        return userRepresentation;
    }

    private static List<CredentialRepresentation> createCredentials(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);

        return List.of(credentialRepresentation);
    }

}
