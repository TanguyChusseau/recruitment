package models.user;

import models.Entity;
import static java.lang.String.format;

public interface User extends Entity {
    String getFirstName();

    String getLastName();

    @Override
    default String getName() {
        return format("%s %s", getFirstName(), getLastName().toUpperCase());
    }
}
