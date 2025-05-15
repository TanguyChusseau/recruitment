package services.restaurant;

import models.restaurant.Meal;
import models.restaurant.Restaurant;

import java.time.LocalDateTime;
import java.util.Map;

public abstract class AbstractRestaurantService {
    public abstract void addMealToRestaurant(Restaurant restaurant, Meal meal);

    public abstract void updateMealPrice(Restaurant restaurant, Meal meal, Double newPrice);

    public abstract Map<LocalDateTime, Double> checkMealPriceHistory(Restaurant restaurant, Meal meal);
}
