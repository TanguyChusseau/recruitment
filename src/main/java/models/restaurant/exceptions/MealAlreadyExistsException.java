package models.restaurant.exceptions;

public class MealAlreadyExistsException extends RuntimeException {
    public MealAlreadyExistsException(String mealName, String restaurantName) {
        super(String.format("Meal %s already exists in restaurant %s", mealName, restaurantName));
    }
}
