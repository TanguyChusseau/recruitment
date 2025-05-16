package services.order;

import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.user.customer.Customer;

import java.util.List;
import java.util.Map;

public abstract class AbstractOrderService {
    public abstract void makeOrder(Restaurant restaurant, Customer customer, List<Meal> meals);

    public abstract void makeOrders(Map<Restaurant,  List<Meal>> restaurantsAndMeals, Customer customer);
}
