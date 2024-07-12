package uz.guideme.userservice.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "basic_users")
public class UserModel {

    public UserModel(Object id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    private Object id;

    private String username;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
