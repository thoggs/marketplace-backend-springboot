package codesumn.com.marketplace_backend.shared.enums;

public enum RolesEnum {
    ADMIN("admin"),
    USER("user"),
    MANAGER("manager"),
    GUEST("guest");

    private final String value;

    RolesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
