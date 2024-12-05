package codesumn.com.marketplace_backend.shared.exceptions.errors;

import lombok.Getter;

@Getter
public class EnumValidationException extends RuntimeException {
    private final String enumName;
    private final String invalidValue;

    public EnumValidationException(String enumName, String invalidValue) {
        super(String.format("Invalid value '%s' for enum %s", invalidValue, enumName));
        this.enumName = enumName;
        this.invalidValue = invalidValue;
    }

}