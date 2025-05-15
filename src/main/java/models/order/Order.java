package models.order;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import models.Entity;
import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.user.customer.Customer;

import static java.lang.String.format;
import static java.time.LocalDate.now;

public class Order implements Entity {

    @Getter
    private final LocalDate date;

    @Getter
    private final Restaurant restaurant;

    @Getter
    private final Customer customer;

    @Getter
    private final List<Meal> meals;

    public Order(Restaurant restaurant, Customer customer, List<Meal> meals) {
        this.date = now();
        this.restaurant = restaurant;
        this.customer = customer;
        this.meals = (meals == null) ? new ArrayList<>() : new ArrayList<>(meals);
    }

    @Override
    public String getName() {
        return format("From client %s - for restaurant %s", this.customer.getName(), this.restaurant.getName());
    }

    public Double getTotalPrice() {
        double totalPrice = 0D;
        int mealIndex = 0;

        Discount discount = new Discount();
        double customerTypeAssociatedDiscount = discount.getCustomerTypeAssociatedDiscount(this.customer.getType());
        double platformLoyaltyDiscount = discount.getCustomerPlatformLoyaltyDiscount(this.customer);
        double restaurantLoyaltyDiscount = discount.getCustomerRestaurantLoyaltyDiscount(this.customer, this.restaurant);

        for (Meal meal : meals) {
            mealIndex += 1;

            // Second meal is free if the order is made less than a week after the last one
            if (customerHasOrderedInThePastSevenDays(this.customer) && mealIndex == 2) continue;

            double totalDiscount = customerTypeAssociatedDiscount + platformLoyaltyDiscount + restaurantLoyaltyDiscount;

            totalPrice += meal.getPrice() * (1 - totalDiscount);
        }

        return totalPrice;
    }

    private boolean customerHasOrderedInThePastSevenDays(Customer customer) {
        return customer.getOrders()
                .stream()
                .anyMatch(order -> order != this && ChronoUnit.DAYS.between(order.date, now()) <= 7);
    }
}
