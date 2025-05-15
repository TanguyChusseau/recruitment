package models.restaurant.exceptions;

public class MealNotFoundException extends RuntimeException {
    public MealNotFoundException(String mealName, String restaurantName) {
        super(String.format("No meal named %s found in restaurant %s", mealName, restaurantName));
    }
}
