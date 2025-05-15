package services.order;

import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.user.customer.Customer;

import java.util.List;

public abstract class AbstractOrderService {
    public abstract void makeOrder(Restaurant restaurant, Customer customer, List<Meal> meals);
}
