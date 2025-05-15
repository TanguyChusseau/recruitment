package models.restaurant.exceptions;

import static java.lang.String.format;

public class MealInvalidPriceException extends RuntimeException {
    public MealInvalidPriceException(String message) {
        super(message);
    }
}
