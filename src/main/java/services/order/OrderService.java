package services.order;

import models.order.Order;
import models.restaurant.Meal;
import models.restaurant.exceptions.MealNotFoundException;
import models.restaurant.Restaurant;
import models.user.customer.Customer;

import java.util.List;

import static java.lang.String.format;

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
}
