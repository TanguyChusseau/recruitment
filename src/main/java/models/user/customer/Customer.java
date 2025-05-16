package models.user.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import models.order.Order;
import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.user.User;
import services.order.AbstractOrderService;
import services.order.OrderService;
import services.restaurant.AbstractRestaurantService;
import services.restaurant.RestaurantService;

public class Customer implements User {

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    private final CustomerType type;

    @Getter
    private final List<Order> orders;

    private final AbstractOrderService orderService;

    private final AbstractRestaurantService restaurantService;

    public Customer(String firstName, String lastName, CustomerType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.orders = new ArrayList<>();
        this.orderService = new OrderService();
        this.restaurantService = new RestaurantService();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void makeOrder(Restaurant restaurant, List<Meal> meals) {
        this.orderService.makeOrder(restaurant, this, meals);
    }

    public void makeOrders(Map<Restaurant, List<Meal>> restaurantsAndMeals) {
        this.orderService.makeOrders(restaurantsAndMeals, this);
    }

    public List<Restaurant> getVegetarianRestaurants(List<Restaurant> restaurants) {
        return this.restaurantService.getVegetarianRestaurants(restaurants);
    }
}
