package models.order;

import models.restaurant.Meal;
import models.restaurant.MealType;
import models.restaurant.Restaurant;
import models.user.customer.Customer;
import models.user.customer.CustomerType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestaurantTest {

    @Test
    void addMeal_whenAddMeal_thenSucceeds() {
        // Given
        Restaurant restaurant = new Restaurant("Dominos");
        Meal pizza = new Meal(restaurant, "Pizza", MealType.CLASSIC, 15.0);

        // When
        restaurant.addMeal(pizza);

        // Then
        assertTrue(restaurant.getMeals().contains(pizza));
    }

    @Test
    void addOrder_whenAddOrder_thenSucceeds() {
        // Given
        Customer customer = new Customer("Jean", "Valjean", CustomerType.STUDENT);
        Restaurant restaurant = new Restaurant("Dominos");
        Meal pizza = new Meal(restaurant, "Pizza", MealType.CLASSIC, 15.0);
        Order order = new Order(restaurant, customer, List.of(pizza));

        // When
        restaurant.addOrder(order);

        // Then
        assertTrue(restaurant.getOrders().contains(order));
    }

    @Test
    void hasMeal_whenRestaurantDoesNotHaveMeal_thenFalse() {
        // Given
        Restaurant restaurant = new Restaurant("Dominos");
        Meal pizza = new Meal(restaurant, "Pizza", MealType.CLASSIC, 15.0);

        // When
        boolean hasMeal = restaurant.hasMeal(pizza.getName());

        // Then
        assertFalse(hasMeal);
    }

    @Test
    void hasMeal_whenRestaurantHasMeal_thenTrue() {
        // Given
        Restaurant restaurant = new Restaurant("Dominos");
        Meal pizza = new Meal(restaurant, "Pizza", MealType.CLASSIC, 15.0);
        restaurant.addMeal(pizza);

        // When
        boolean hasMeal = restaurant.hasMeal(pizza.getName());

        // Then
        assertTrue(hasMeal);
    }

    @Test
    void isVegetarian_whenRestaurantDoesNotOnlyHaveVegetarianMeals_thenFalse() {
        // Given
        Restaurant restaurant = new Restaurant("Dominos");
        Meal classicPizza = new Meal(restaurant, "Pizza", MealType.CLASSIC, 15.0);
        Meal vegetarianPizza = new Meal(restaurant, "Vegetarian Pizza", MealType.VEGETARIAN, 16.0);
        restaurant.addMeal(classicPizza);
        restaurant.addMeal(vegetarianPizza);

        // When
        boolean isVegetarian = restaurant.isVegetarian();

        // Then
        assertFalse(isVegetarian);
    }

    @Test
    void isVegetarian_whenRestaurantOnlyHasVegetarianMeals_thenTrue() {
        // Given
        Restaurant restaurant = new Restaurant("Dominos");
        Meal vegetarianBurger = new Meal(restaurant, "Burger vege", MealType.VEGETARIAN, 15.0);
        Meal vegetarianPizza = new Meal(restaurant, "Vegetarian Pizza", MealType.VEGETARIAN, 16.0);
        restaurant.addMeal(vegetarianBurger);
        restaurant.addMeal(vegetarianPizza);

        // When
        boolean isVegetarian = restaurant.isVegetarian();

        // Then
        assertTrue(isVegetarian);
    }
}
