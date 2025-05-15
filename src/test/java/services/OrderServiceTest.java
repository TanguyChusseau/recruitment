package services;

import models.restaurant.Meal;
import models.restaurant.exceptions.MealNotFoundException;
import models.restaurant.Restaurant;
import models.user.customer.Customer;
import models.user.customer.CustomerType;
import org.junit.jupiter.api.Test;
import services.order.OrderService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    @Test
    void makeOrder_whenMealNotFound_thenThrows() {
        // Given
        OrderService orderService = new OrderService();

        Restaurant restaurant = new Restaurant("Bio Burger");
        Customer customer = new Customer("Jean", "Valjean", CustomerType.STUDENT);

        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Meal nonExistingMeal = new Meal(restaurant, "Non existing meal", 12.0);

        assertThrows(MealNotFoundException.class,
                () -> orderService.makeOrder(restaurant, customer, List.of(nonExistingMeal)),
                "No meal named Non existing meal found in restaurant Bio Burger"
        );
    }

    @Test
    void makeOrder_whenMealFound_thenSucceeds() {
        // Given
        OrderService orderService = new OrderService();

        Restaurant restaurant = new Restaurant("Bio Burger");
        Customer customer = new Customer("Jean", "Valjean", CustomerType.STUDENT);

        Meal existingMeal = new Meal(restaurant, "Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When
        orderService.makeOrder(restaurant, customer, List.of(existingMeal));

        // Then
        assertEquals(1, restaurant.getOrders().size());
        assertEquals(1, customer.getOrders().size());
    }

    @Test
    void makeOrder_whenMealFound_thenAddsOrderToRestaurantAndCustomer() {
        // Given
        OrderService orderService = new OrderService();

        Restaurant restaurant = new Restaurant("Bio Burger");
        Customer customer = new Customer("Jean", "Valjean", CustomerType.STUDENT);

        Meal existingMeal = new Meal(restaurant,"Tenders", 9.0);
        restaurant.addMeal(existingMeal);

        // When
        orderService.makeOrder(restaurant, customer, List.of(existingMeal));

        // Then
        assertEquals(1, restaurant.getOrders().size());
        assertEquals(1, customer.getOrders().size());
    }
}