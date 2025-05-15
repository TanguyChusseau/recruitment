package models.restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import models.Entity;
import models.order.Order;

public class Restaurant implements Entity {
    @Getter
    private final String name;

    private final List<Meal> meals;

    private final List<Order> orders;

    public Restaurant(String name) {
        this.name = name;
        this.meals = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public List<Meal> getMeals() {
        return Collections.unmodifiableList(this.meals);
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(this.orders);
    }

    public boolean hasMeal(String mealName) {
        return this.meals.stream().anyMatch(meal -> meal.getName().equalsIgnoreCase(mealName));
    }

    public void addMeal(Meal meal) {
        this.meals.add(meal);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
