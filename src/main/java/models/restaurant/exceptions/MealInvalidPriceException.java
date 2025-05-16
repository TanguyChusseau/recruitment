package models.restaurant.exceptions;

public class MealInvalidPriceException extends RuntimeException {
    public MealInvalidPriceException(String message) {
        super(message);
    }
}
