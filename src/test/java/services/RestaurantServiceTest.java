package services;

import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.restaurant.exceptions.MealAlreadyExistsException;
import models.restaurant.exceptions.MealNotFoundException;
import models.restaurant.exceptions.MealInvalidPriceException;
import org.junit.jupiter.api.Test;
import services.restaurant.RestaurantService;

import java.time.LocalDateTime;
import java.util.Map;

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
    void addMeal_whenMealDoesNotAlreadyExist_thenSucceeds() {
        // Given
        RestaurantService restaurantService = new RestaurantService();
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Meal newMeal = new Meal(restaurant, "Burger vege", 9.0);
        assertDoesNotThrow(() -> restaurantService.addMealToRestaurant(restaurant, newMeal));
    }

    @Test
    void updateMealPrice_whenMealDoesNotExist_thenThrows() {
        // Given
        RestaurantService restaurantService = new RestaurantService();
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Double newPrice = 13.0;
        Meal nonExistingMeal = new Meal(restaurant, "Burger vege", newPrice);

        assertThrows(MealNotFoundException.class,
                () -> restaurantService.updateMealPrice(restaurant, nonExistingMeal, newPrice),
                "Meal Non existing meal not found in restaurant Bio Burger"
        );
    }

    @Test
    void updateMealPrice_whenNewPriceIsNull_thenThrows() {
        // Given
        RestaurantService restaurantService = new RestaurantService();
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        assertThrows(MealInvalidPriceException.class,
                () -> restaurantService.updateMealPrice(restaurant, existingMeal, null),
                "Meal price cannot be null"
        );
    }

    @Test
    void updateMealPrice_whenNewPriceIsNegative_thenThrows() {
        // Given
        RestaurantService restaurantService = new RestaurantService();
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Double newPrice = -13.0;

        assertThrows(MealInvalidPriceException.class,
                () -> restaurantService.updateMealPrice(restaurant, existingMeal, newPrice),
                "Meal price cannot be negative"
        );
    }

    @Test
    void updateMealPrice_whenNewPriceIsTheSame_thenThrows() {
        // Given
        RestaurantService restaurantService = new RestaurantService();
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Double newPrice = 9.0;

        assertThrows(MealInvalidPriceException.class,
                () -> restaurantService.updateMealPrice(restaurant, existingMeal, newPrice),
                "Meal price cannot be negative: -13.0"
        );
    }

    @Test
    void updateMealPrice_whenMealExists_whenNewPriceIsPositive_thenSucceeds() {
        // Given
        RestaurantService restaurantService = new RestaurantService();
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When
        Double newPrice = 12.0;
        restaurantService.updateMealPrice(restaurant, existingMeal, newPrice);

        // Then
        assertEquals(newPrice, existingMeal.getPrice());
    }

    @Test
    void checkMealPriceHistory_whenMealDoesNotExist_thenThrows() {
        // Given
        RestaurantService restaurantService = new RestaurantService();
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Meal nonExistingMeal = new Meal(restaurant, "Burger vege", 12.0);

        assertThrows(MealNotFoundException.class,
                () -> restaurantService.checkMealPriceHistory(restaurant, nonExistingMeal),
                "Meal Non existing meal not found in restaurant Bio Burger"
        );
    }

    @Test
    void checkMealPriceHistory_whenMealExists_thenSucceeds() {
        // Given
        RestaurantService restaurantService = new RestaurantService();
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When
        restaurantService.updateMealPrice(restaurant, existingMeal, 12.0);
        Map<LocalDateTime, Double> mealPriceHistory = restaurantService.checkMealPriceHistory(restaurant, existingMeal);

        // Then
        assertEquals(2, mealPriceHistory.size());
    }
}