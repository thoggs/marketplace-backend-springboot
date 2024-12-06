package codesumn.com.marketplace_backend.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RolesEnum {
    ADMIN("admin"),
    USER("user"),
    MANAGER("manager"),
    GUEST("guest");

    private final String value;

    public static RolesEnum fromValue(String value) {
        for (RolesEnum role : RolesEnum.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        return USER;
    }
}
