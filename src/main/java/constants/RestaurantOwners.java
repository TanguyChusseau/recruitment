package constants;

import lombok.Getter;

@Getter
public enum RestaurantOwners {
    ROBERT_DUPONT("Robert", "Dupont"),
    MAGALI_NOEL("Magali", "Noel"),
    NICOLAS_BENOIT("Nicolas", "Benoit");

    private final String firstName;
    private final String lastName;

    RestaurantOwners(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}