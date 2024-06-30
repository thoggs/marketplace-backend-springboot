package codesumn.com.marketplace_backend.app.models;

import codesumn.com.marketplace_backend.dtos.AuthUserRecordDto;
import codesumn.com.marketplace_backend.dtos.UserRecordDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "USERS")
public class UserModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    public UserModel() {
    }

    public UserModel(UserRecordDto userRecordDto) {
        this.firstName = userRecordDto.firstName();
        this.lastName = userRecordDto.lastName();
        this.email = userRecordDto.email();
        this.password = userRecordDto.password();
        this.role = userRecordDto.role();
    }

    public UserModel(AuthUserRecordDto authUserDto) {
        this.firstName = authUserDto.firstName();
        this.lastName = authUserDto.lastName();
        this.email = authUserDto.email();
        this.role = authUserDto.role();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
