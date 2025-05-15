package services.restaurant;

import models.restaurant.Meal;
import models.restaurant.Restaurant;

public abstract class AbstractRestaurantService {
    public abstract void addMealToRestaurant(Restaurant restaurant, Meal meal);
}
