package services.restaurant;

import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.restaurant.exceptions.MealAlreadyExistsException;

public class RestaurantService extends AbstractRestaurantService {
    public void addMealToRestaurant(Restaurant restaurant, Meal meal) {
        if (restaurant.hasMeal(meal.getName())) {
            throw new MealAlreadyExistsException(meal.getName(), restaurant.getName());
        }

        restaurant.addMeal(meal);
    }
}
