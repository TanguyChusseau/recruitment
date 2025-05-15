package services;

import models.restaurant.Meal;
import models.restaurant.MealType;
import models.restaurant.Restaurant;
import models.restaurant.exceptions.MealAlreadyExistsException;
import models.restaurant.exceptions.MealNotFoundException;
import models.restaurant.exceptions.MealInvalidPriceException;
import org.junit.jupiter.api.Test;
import services.restaurant.RestaurantService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {
    private final RestaurantService restaurantService = new RestaurantService();

    @Test
    void addMeal_whenMealAlreadyExists_thenThrows() {
        // Given
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Meal newMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 12.0);

        assertThrows(MealAlreadyExistsException.class,
                () -> restaurantService.addMealToRestaurant(restaurant, newMeal),
                "Meal Non existing meal already exists in restaurant Bio Burger"
        );
    }

    @Test
    void addMeal_whenMealDoesNotAlreadyExist_thenSucceeds() {
        // Given
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Meal newMeal = new Meal(restaurant, "Burger vege", MealType.VEGETARIAN, 9.0);
        assertDoesNotThrow(() -> restaurantService.addMealToRestaurant(restaurant, newMeal));
    }

    @Test
    void updateMealPrice_whenMealDoesNotExist_thenThrows() {
        // Given
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Double newPrice = 13.0;
        Meal nonExistingMeal = new Meal(restaurant, "Burger vege", MealType.VEGETARIAN, newPrice);

        assertThrows(MealNotFoundException.class,
                () -> restaurantService.updateMealPrice(restaurant, nonExistingMeal, newPrice),
                "Meal Non existing meal not found in restaurant Bio Burger"
        );
    }

    @Test
    void updateMealPrice_whenNewPriceIsNull_thenThrows() {
        // Given
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
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
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
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
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
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
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
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
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Meal nonExistingMeal = new Meal(restaurant, "Burger vege", MealType.VEGETARIAN, 12.0);

        assertThrows(MealNotFoundException.class,
                () -> restaurantService.checkMealPriceHistory(restaurant, nonExistingMeal),
                "Meal Non existing meal not found in restaurant Bio Burger"
        );
    }

    @Test
    void checkMealPriceHistory_whenMealExists_thenSucceeds() {
        // Given
        Restaurant restaurant = new Restaurant("Bio Burger");
        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
        restaurant.addMeal(existingMeal);

        // When
        restaurantService.updateMealPrice(restaurant, existingMeal, 12.0);
        Map<LocalDateTime, Double> mealPriceHistory = restaurantService.checkMealPriceHistory(restaurant, existingMeal);

        // Then
        assertEquals(2, mealPriceHistory.size());
    }

    @Test
    void getVegetarianRestaurants_whenNoRestaurantIsVegetarian_thenEmpty() {
        // Given
        Restaurant classicRestaurant = new Restaurant("Pas Bio Burger");
        Restaurant otherClassicRestaurant = new Restaurant("Pas Bio Non Plus Burger");

        Meal classicMeal = new Meal(classicRestaurant, "Burger pas vege", MealType.CLASSIC, 12.0);
        Meal otherClassicMeal = new Meal(classicRestaurant, "Tenders pas vege", MealType.CLASSIC, 9.0);
        Meal vegetarianMeal = new Meal(otherClassicRestaurant, "Burger vege", MealType.CLASSIC, 13.0);

        classicRestaurant.addMeal(classicMeal);
        classicRestaurant.addMeal(otherClassicMeal);

        otherClassicRestaurant.addMeal(classicMeal);
        otherClassicRestaurant.addMeal(vegetarianMeal);

        // When
        List<Restaurant> vegetarianRestaurants =
                restaurantService.getVegetarianRestaurants(List.of(classicRestaurant, otherClassicRestaurant));

        // Then
        assertEquals(0, vegetarianRestaurants.size());
    }

    @Test
    void getVegetarianRestaurants_whenSomeRestaurantsAreVegetarian_thenReturnsVegetarianRestaurants() {
        // Given
        Restaurant classicRestaurant = new Restaurant("Pas Bio Burger");
        Restaurant vegetarianRestaurant = new Restaurant("Bio Burger");

        Meal classicMeal = new Meal(classicRestaurant, "Burger pas vege", MealType.CLASSIC, 12.0);
        Meal otherClassicMeal = new Meal(classicRestaurant, "Tenders", MealType.CLASSIC, 9.0);

        classicRestaurant.addMeal(classicMeal);
        classicRestaurant.addMeal(otherClassicMeal);

        Meal vegetarianMeal = new Meal(vegetarianRestaurant, "Burger vege", MealType.VEGETARIAN, 13.0);
        Meal otherVegetarianMeal = new Meal(vegetarianRestaurant, "Tenders vege", MealType.VEGETARIAN, 10.0);

        vegetarianRestaurant.addMeal(vegetarianMeal);
        vegetarianRestaurant.addMeal(otherVegetarianMeal);

        // When
        List<Restaurant> vegetarianRestaurants =
                restaurantService.getVegetarianRestaurants(List.of(classicRestaurant, vegetarianRestaurant));

        // Then
        assertEquals(1, vegetarianRestaurants.size());
        assertTrue(vegetarianRestaurants.contains(vegetarianRestaurant));
    }
}