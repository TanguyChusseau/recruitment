package services.order;

import models.restaurant.Meal;
import models.restaurant.MealType;
import models.restaurant.exceptions.MealNotFoundException;
import models.restaurant.Restaurant;
import models.user.customer.Customer;
import models.user.customer.CustomerType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    OrderService orderService = new OrderService();

    @Test
    void makeOrder_whenMealNotFound_thenThrows() {
        // Given
        Restaurant restaurant = new Restaurant("Bio Burger");
        Customer customer = new Customer("Jean", "Valjean", CustomerType.STUDENT);

        Meal existingMeal = new Meal(restaurant, "Tenders", MealType.CLASSIC, 9.0);
        restaurant.addMeal(existingMeal);

        // When and Then
        Meal nonExistingMeal = new Meal(restaurant, "Non existing meal", MealType.CLASSIC, 12.0);

        assertThrows(MealNotFoundException.class,
                () -> orderService.makeOrder(restaurant, customer, List.of(nonExistingMeal)),
                "No meal named Non existing meal found in restaurant Bio Burger"
        );
    }

    @Test
    void makeOrder_whenMealFound_thenAddsOrderToRestaurantAndCustomer() {
        // Given
        Restaurant restaurant = new Restaurant("Bio Burger");
        Customer customer = new Customer("Jean", "Valjean", CustomerType.STUDENT);

        Meal existingMeal = new Meal(restaurant,"Tenders", MealType.CLASSIC, 9.0);
        restaurant.addMeal(existingMeal);

        // When
        orderService.makeOrder(restaurant, customer, List.of(existingMeal));

        // Then
        assertEquals(1, restaurant.getOrders().size());
        assertEquals(1, customer.getOrders().size());
    }

    @Test
    void makeOrders_whenMealNotFound_thenThrows() {
        // Given
        Restaurant bioBurger = new Restaurant("Bio Burger");
        Restaurant bioTenders = new Restaurant("Bio Tenders");
        Customer customer = new Customer("Jean", "Valjean", CustomerType.STUDENT);

        Meal burger = new Meal(bioBurger, "Tenders", MealType.VEGETARIAN, 13.0);
        Meal tenders = new Meal(bioTenders, "Tenders", MealType.VEGETARIAN, 9.0);
        bioBurger.addMeal(burger);
        bioTenders.addMeal(tenders);

        // When and Then
        Meal nonExistingMeal = new Meal(bioTenders, "Non existing meal", MealType.CLASSIC, 12.0);
        Map<Restaurant, List<Meal>> restaurantsAndMeals =
                Map.of(bioBurger, List.of(burger), bioTenders, List.of(tenders, nonExistingMeal));

        assertThrows(MealNotFoundException.class,
                () -> orderService.makeOrders(restaurantsAndMeals, customer),
                "No meal named Non existing meal found in restaurant Bio Tenders"
        );
    }

    @Test
    void makeOrders_whenMealFound_thenAddsOrdersToRestaurantsAndCustomer() {
        // Given
        Restaurant bioBurger = new Restaurant("Bio Burger");
        Restaurant bioTenders = new Restaurant("Bio Tenders");
        Customer customer = new Customer("Jean", "Valjean", CustomerType.STUDENT);

        Meal burger = new Meal(bioBurger, "Tenders", MealType.VEGETARIAN, 13.0);
        Meal tenders = new Meal(bioTenders, "Tenders", MealType.VEGETARIAN, 9.0);
        bioBurger.addMeal(burger);
        bioTenders.addMeal(tenders);

        // When
        Map<Restaurant, List<Meal>> restaurantsAndMeals =
                Map.of(bioBurger, List.of(burger), bioTenders, List.of(tenders));

        orderService.makeOrders(restaurantsAndMeals, customer);

        // Then
        assertEquals(1, bioBurger.getOrders().size());
        assertEquals(1, bioTenders.getOrders().size());
        assertEquals(2, customer.getOrders().size());
    }
}