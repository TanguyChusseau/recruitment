package services.restaurant;

import models.restaurant.Meal;
import models.restaurant.MealType;
import models.restaurant.Restaurant;
import models.restaurant.exceptions.MealAlreadyExistsException;
import models.restaurant.exceptions.MealNotFoundException;
import models.restaurant.exceptions.MealInvalidPriceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class RestaurantService extends AbstractRestaurantService {
    public void addMealToRestaurant(Restaurant restaurant, Meal meal) {
        if (restaurant.hasMeal(meal.getName())) {
            throw new MealAlreadyExistsException(meal.getName(), restaurant.getName());
        }

        restaurant.addMeal(meal);
    }

    public void updateMealPrice(Restaurant restaurant, Meal meal, Double newPrice) {
        if (!restaurant.hasMeal(meal.getName())) {
            throw new MealNotFoundException(meal.getName(), restaurant.getName());
        }

        if (newPrice == null) {
            throw new MealInvalidPriceException("New price cannot be null");
        }

        if (newPrice < 0) {
            throw new MealInvalidPriceException("New price cannot be negative");
        }

        if (newPrice.equals(meal.getPrice())) {
            throw new MealInvalidPriceException("New price cannot be the same as the old price");
        }

        meal.updatePrice(newPrice);
    }

    public Map<LocalDateTime, Double> checkMealPriceHistory(Restaurant restaurant, Meal meal) {
        if (!restaurant.hasMeal(meal.getName())) {
            throw new MealNotFoundException(meal.getName(), restaurant.getName());
        }

        return meal.getPriceHistory();
    }

    public List<Restaurant> getVegetarianRestaurants(List<Restaurant> restaurants) {
        return restaurants.stream().filter(Restaurant::isVegetarian).toList();
    }
}
