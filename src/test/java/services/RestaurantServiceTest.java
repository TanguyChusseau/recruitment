package services;

import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.restaurant.exceptions.MealAlreadyExistsException;
import org.junit.jupiter.api.Test;
import services.restaurant.RestaurantService;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {
    @Test
    void addMeal_whenMealAlreadyExists_thenThrows() {
        // Given
        RestaurantService restaurantService = new RestaurantService();

        Restaurant restaurant = new Restaurant("Bio Burger");

        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Meal newMeal = new Meal(restaurant, "Tenders", 12.0);
        assertThrows(MealAlreadyExistsException.class,
                () -> restaurantService.addMealToRestaurant(restaurant, newMeal),
                "Meal Non existing meal already exists in restaurant Bio Burger"
        );
    }

    @Test
    void addMeal_whenMealDoesNotExists_thenSucceeds() {
        // Given
        RestaurantService restaurantService = new RestaurantService();

        Restaurant restaurant = new Restaurant("Bio Burger");

        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Meal newMeal = new Meal(restaurant, "Burger vege", 9.0);
        assertDoesNotThrow(() -> restaurantService.addMealToRestaurant(restaurant, newMeal));
    }
}