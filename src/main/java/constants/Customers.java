package constants;

import lombok.Getter;
import models.user.customer.CustomerType;

@Getter
public enum Customers {
    CATHERINE_ZWAHLEN("Catherine", "Zwahlen", CustomerType.OTHER),
    CLEMENTINE_DELERCE("Clementine", "Delerce", CustomerType.OTHER);

    private final String firstName;
    private final String lastName;
    private final CustomerType type;

    Customers(String firstName, String lastName, CustomerType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }
}