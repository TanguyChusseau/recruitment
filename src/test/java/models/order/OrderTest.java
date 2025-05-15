package models.order;

import models.restaurant.Meal;
import models.restaurant.MealType;
import models.restaurant.Restaurant;
import models.user.customer.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static models.user.customer.CustomerType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    @Test
    void getTotalPrice_whenCustomerTypeChild_when1stOrder_thenChildDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", CHILD);
        Restaurant restaurant = new Restaurant("The restaurant");

        Meal meal1 = new Meal(restaurant, "Meal 1", MealType.CLASSIC, 15.0);
        Meal meal2 = new Meal(restaurant, "Meal 2", MealType.CLASSIC, 10.0);
        restaurant.addMeal(meal1);
        restaurant.addMeal(meal2);

        Order newOrder = new Order(restaurant, customer, List.of(meal1, meal2));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.CHILD_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = (meal1.getPrice() + meal2.getPrice()) * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeChild_when1stOrder_whenHasOrderedInTheLastSevenDays_thenChildDiscount_thenSecondMealIsFree() {
        // Given
        Customer customer = new Customer("Ba", "Bar", CHILD);
        Restaurant restaurant = new Restaurant("The restaurant");

        Meal meal1 = new Meal(restaurant, "Meal 1", MealType.CLASSIC, 15.0);
        Meal meal2 = new Meal(restaurant, "Meal 2", MealType.CLASSIC, 10.0);
        restaurant.addMeal(meal1);
        restaurant.addMeal(meal2);

        Order oldOrder = new Order(restaurant, customer, List.of(meal1, meal2));
        Order newOrder = new Order(restaurant, customer, List.of(meal1, meal2));
        customer.addOrder(oldOrder);
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.CHILD_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = (meal1.getPrice()) * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeChild_when5thOrderInSameRestaurant_thenChildAndRestaurantDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", CHILD);
        Restaurant restaurant = new Restaurant("The restaurant");

        Meal meal = new Meal(restaurant, "Meal", MealType.CLASSIC, 10.0);
        restaurant.addMeal(meal);

        Order order1 = new Order(restaurant, customer, List.of(meal));
        Order order2 = new Order(restaurant, customer, List.of(meal));
        Order order3 = new Order(restaurant, customer, List.of(meal));
        Order order4 = new Order(restaurant, customer, List.of(meal));

        List<Order> customerOrders = List.of(order1, order2, order3, order4);
        customerOrders.forEach(customer::addOrder);

        Order newOrder = new Order(restaurant, customer, List.of(meal));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.CHILD_DISCOUNT_VALUE + Discount.RESTAURANT_LOYALTY_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = meal.getPrice() * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeChild_when10thOrderInSameRestaurant_thenChildAndPlatformAndRestaurantDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", CHILD);
        Restaurant dominos = new Restaurant("Dominos");

        Meal baconGroovy = new Meal(dominos, "Bacon Groovy", MealType.CLASSIC, 10.0);
        dominos.addMeal(baconGroovy);

        List<Order> customerOrders = generateCustomerOrders(dominos, customer, baconGroovy);
        customerOrders.forEach(customer::addOrder);

        Order newOrder = new Order(dominos, customer, List.of(baconGroovy));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.CHILD_DISCOUNT_VALUE +
                Discount.PLATFORM_LOYALTY_DISCOUNT_VALUE +
                Discount.RESTAURANT_LOYALTY_DISCOUNT_VALUE;

        Double expectedOrderTotalPrice = baconGroovy.getPrice() * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeChild_when10thOrderInDifferentRestaurants_thenChildAndPlatformDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", CHILD);
        Restaurant dominos = new Restaurant("Dominos");
        Restaurant pizzaHut = new Restaurant("Pizza Hut");

        Meal baconGroovy = new Meal(dominos, "Bacon Groovy", MealType.CLASSIC, 10.0);
        Meal indianCurryPizza = new Meal(pizzaHut, "Indian Curry", MealType.CLASSIC, 12.0);
        dominos.addMeal(baconGroovy);
        pizzaHut.addMeal(indianCurryPizza);

        List<Order> customerOrders = generateCustomerOrders(dominos, customer, baconGroovy);
        customerOrders.forEach(customer::addOrder);

        Order newOrder = new Order(pizzaHut, customer, List.of(indianCurryPizza));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.CHILD_DISCOUNT_VALUE + Discount.PLATFORM_LOYALTY_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = indianCurryPizza.getPrice() * (1 - expectedOrderTotalDiscount);
        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeStudent_when1stOrder_thenStudentDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", STUDENT);
        Restaurant restaurant = new Restaurant("The restaurant");

        Meal meal1 = new Meal(restaurant, "Meal 1", MealType.CLASSIC, 15.0);
        Meal meal2 = new Meal(restaurant, "Meal 2", MealType.CLASSIC, 10.0);
        restaurant.addMeal(meal1);
        restaurant.addMeal(meal2);

        Order newOrder = new Order(restaurant, customer, List.of(meal1, meal2));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.STUDENT_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = (meal1.getPrice() + meal2.getPrice()) * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeStudent_when1stOrder_whenHasOrderedInTheLastSevenDays_thenStudentDiscount_thenSecondMealIsFree() {
        // Given
        Customer customer = new Customer("Ba", "Bar", STUDENT);
        Restaurant restaurant = new Restaurant("The restaurant");

        Meal meal1 = new Meal(restaurant, "Meal 1", MealType.CLASSIC, 15.0);
        Meal meal2 = new Meal(restaurant, "Meal 2", MealType.CLASSIC, 10.0);
        restaurant.addMeal(meal1);
        restaurant.addMeal(meal2);

        Order oldOrder = new Order(restaurant, customer, List.of(meal1, meal2));
        Order newOrder = new Order(restaurant, customer, List.of(meal1, meal2));
        customer.addOrder(oldOrder);
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.STUDENT_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = (meal1.getPrice()) * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeStudent_when5thOrderInSameRestaurant_thenStudentAndRestaurantDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", STUDENT);
        Restaurant restaurant = new Restaurant("The restaurant");

        Meal meal = new Meal(restaurant, "Meal", MealType.CLASSIC, 10.0);
        restaurant.addMeal(meal);

        Order order1 = new Order(restaurant, customer, List.of(meal));
        Order order2 = new Order(restaurant, customer, List.of(meal));
        Order order3 = new Order(restaurant, customer, List.of(meal));
        Order order4 = new Order(restaurant, customer, List.of(meal));

        List<Order> customerOrders = List.of(order1, order2, order3, order4);
        customerOrders.forEach(customer::addOrder);

        Order newOrder = new Order(restaurant, customer, List.of(meal));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.STUDENT_DISCOUNT_VALUE + Discount.RESTAURANT_LOYALTY_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = meal.getPrice() * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeStudent_when10thOrderInSameRestaurant_thenStudentAndPlatformAndRestaurantDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", STUDENT);
        Restaurant dominos = new Restaurant("Dominos");

        Meal baconGroovy = new Meal(dominos, "Bacon Groovy", MealType.CLASSIC, 10.0);
        dominos.addMeal(baconGroovy);

        List<Order> customerOrders = generateCustomerOrders(dominos, customer, baconGroovy);
        customerOrders.forEach(customer::addOrder);

        Order newOrder = new Order(dominos, customer, List.of(baconGroovy));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.STUDENT_DISCOUNT_VALUE +
                Discount.PLATFORM_LOYALTY_DISCOUNT_VALUE +
                Discount.RESTAURANT_LOYALTY_DISCOUNT_VALUE;

        Double expectedOrderTotalPrice = baconGroovy.getPrice() * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeStudent_when10thOrderInDifferentRestaurants_thenStudentAndPlatformDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", STUDENT);
        Restaurant dominos = new Restaurant("Dominos");
        Restaurant pizzaHut = new Restaurant("Pizza Hut");

        Meal baconGroovy = new Meal(dominos, "Bacon Groovy", MealType.CLASSIC, 10.0);
        Meal indianCurryPizza = new Meal(pizzaHut, "Indian Curry", MealType.CLASSIC, 12.0);
        dominos.addMeal(baconGroovy);
        pizzaHut.addMeal(indianCurryPizza);

        List<Order> customerOrders = generateCustomerOrders(dominos, customer, baconGroovy);
        customerOrders.forEach(customer::addOrder);

        Order newOrder = new Order(pizzaHut, customer, List.of(indianCurryPizza));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.STUDENT_DISCOUNT_VALUE + Discount.PLATFORM_LOYALTY_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = indianCurryPizza.getPrice() * (1 - expectedOrderTotalDiscount);
        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeOther_when1stOrder_thenOtherDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", OTHER);
        Restaurant restaurant = new Restaurant("The restaurant");

        Meal meal1 = new Meal(restaurant, "Meal 1", MealType.CLASSIC, 15.0);
        Meal meal2 = new Meal(restaurant, "Meal 2", MealType.CLASSIC, 10.0);
        restaurant.addMeal(meal1);
        restaurant.addMeal(meal2);

        Order newOrder = new Order(restaurant, customer, List.of(meal1, meal2));
        customer.addOrder(newOrder);

        // When
        Double expectedOrderTotalPrice = (meal1.getPrice() + meal2.getPrice());

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeOther_when1stOrder_whenHasOrderedInTheLastSevenDays_thenNoDiscount_thenSecondMealIsFree() {
        // Given
        Customer customer = new Customer("Ba", "Bar", OTHER);
        Restaurant restaurant = new Restaurant("The restaurant");

        Meal meal1 = new Meal(restaurant, "Meal 1", MealType.CLASSIC, 15.0);
        Meal meal2 = new Meal(restaurant, "Meal 2", MealType.CLASSIC, 10.0);
        restaurant.addMeal(meal1);
        restaurant.addMeal(meal2);

        Order oldOrder = new Order(restaurant, customer, List.of(meal1, meal2));
        Order newOrder = new Order(restaurant, customer, List.of(meal1, meal2));
        customer.addOrder(oldOrder);
        customer.addOrder(newOrder);

        // When
        Double expectedOrderTotalPrice = (meal1.getPrice());

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeOther_when5thOrderInSameRestaurant_thenOtherAndRestaurantDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", OTHER);
        Restaurant restaurant = new Restaurant("The restaurant");

        Meal meal = new Meal(restaurant, "Meal", MealType.CLASSIC, 10.0);
        restaurant.addMeal(meal);

        Order order1 = new Order(restaurant, customer, List.of(meal));
        Order order2 = new Order(restaurant, customer, List.of(meal));
        Order order3 = new Order(restaurant, customer, List.of(meal));
        Order order4 = new Order(restaurant, customer, List.of(meal));

        List<Order> customerOrders = List.of(order1, order2, order3, order4);
        customerOrders.forEach(customer::addOrder);

        Order newOrder = new Order(restaurant, customer, List.of(meal));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.RESTAURANT_LOYALTY_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = meal.getPrice() * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeOther_when10thOrderInSameRestaurant_thenOtherAndPlatformAndRestaurantDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", OTHER);
        Restaurant dominos = new Restaurant("Dominos");

        Meal baconGroovy = new Meal(dominos, "Bacon Groovy", MealType.CLASSIC, 10.0);
        dominos.addMeal(baconGroovy);

        List<Order> customerOrders = generateCustomerOrders(dominos, customer, baconGroovy);
        customerOrders.forEach(customer::addOrder);

        Order newOrder = new Order(dominos, customer, List.of(baconGroovy));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.PLATFORM_LOYALTY_DISCOUNT_VALUE +
                Discount.RESTAURANT_LOYALTY_DISCOUNT_VALUE;

        Double expectedOrderTotalPrice = baconGroovy.getPrice() * (1 - expectedOrderTotalDiscount);

        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    @Test
    void getTotalPrice_whenCustomerTypeOther_when10thOrderInDifferentRestaurants_thenOtherAndPlatformDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", OTHER);
        Restaurant dominos = new Restaurant("Dominos");
        Restaurant pizzaHut = new Restaurant("Pizza Hut");

        Meal baconGroovy = new Meal(dominos, "Bacon Groovy", MealType.CLASSIC, 10.0);
        Meal indianCurryPizza = new Meal(pizzaHut, "Indian Curry", MealType.CLASSIC, 12.0);
        dominos.addMeal(baconGroovy);
        pizzaHut.addMeal(indianCurryPizza);

        List<Order> customerOrders = generateCustomerOrders(dominos, customer, baconGroovy);
        customerOrders.forEach(customer::addOrder);

        Order newOrder = new Order(pizzaHut, customer, List.of(indianCurryPizza));
        customer.addOrder(newOrder);

        // When
        double expectedOrderTotalDiscount = Discount.PLATFORM_LOYALTY_DISCOUNT_VALUE;
        Double expectedOrderTotalPrice = indianCurryPizza.getPrice() * (1 - expectedOrderTotalDiscount);
        Double newOrderTotalPrice = newOrder.getTotalPrice();

        // Then
        assertEquals(expectedOrderTotalPrice, newOrderTotalPrice);
    }

    private static List<Order> generateCustomerOrders(Restaurant dominos, Customer customer, Meal meal) {
        Order order1 = new Order(dominos, customer, List.of(meal));
        Order order2 = new Order(dominos, customer, List.of(meal));
        Order order3 = new Order(dominos, customer, List.of(meal));
        Order order4 = new Order(dominos, customer, List.of(meal));
        Order order5 = new Order(dominos, customer, List.of(meal));
        Order order6 = new Order(dominos, customer, List.of(meal));
        Order order7 = new Order(dominos, customer, List.of(meal));
        Order order8 = new Order(dominos, customer, List.of(meal));
        Order order9 = new Order(dominos, customer, List.of(meal));

        return List.of(order1, order2, order3, order4, order5, order6, order7, order8, order9);
    }
}