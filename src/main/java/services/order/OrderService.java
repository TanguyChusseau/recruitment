package services.order;

import models.order.Order;
import models.restaurant.Meal;
import models.restaurant.exceptions.MealNotFoundException;
import models.restaurant.Restaurant;
import models.user.customer.Customer;

import java.util.List;
import java.util.Map;

public class OrderService extends AbstractOrderService {
    public void makeOrder(Restaurant restaurant, Customer customer, List<Meal> meals) {
        for (Meal meal : meals) {
            if (!restaurant.hasMeal(meal.getName())) {
                throw new MealNotFoundException( meal.getName(), restaurant.getName());
            }
        }

        Order order = new Order(restaurant, customer, meals);

        restaurant.addOrder(order);
        customer.addOrder(order);
    }

    public void makeOrders(Map<Restaurant,  List<Meal>> restaurantsAndMeals, Customer customer) {
        for (Map.Entry<Restaurant, List<Meal>> restaurantAndMeals : restaurantsAndMeals.entrySet()) {
            Restaurant restaurant = restaurantAndMeals.getKey();
            List<Meal> meals = restaurantAndMeals.getValue();

            this.makeOrder(restaurant, customer, meals);
        }
    }
}
