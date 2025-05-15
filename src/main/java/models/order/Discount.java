package models.order;

import lombok.Getter;
import models.restaurant.Restaurant;
import models.user.customer.Customer;
import models.user.customer.CustomerType;

public class Discount {

    @Getter
    private final Double value;

    protected static final double CHILD_DISCOUNT_VALUE = 0.50;
    protected static final double STUDENT_DISCOUNT_VALUE = 0.25;
    protected static final double PLATFORM_LOYALTY_DISCOUNT_VALUE = 0.15;
    protected static final double RESTAURANT_LOYALTY_DISCOUNT_VALUE = 0.10;

    protected static final int NUMBER_OF_ORDERS_TO_TRIGGER_PLATFORM_LOYALTY_DISCOUNT = 10;
    protected static final int NUMBER_OF_ORDERS_TO_TRIGGER_RESTAURANT_LOYALTY_DISCOUNT = 5;

    public Discount() {
        this.value = 0.0;
    }

    Double getCustomerTypeAssociatedDiscount(CustomerType customerType) {
        return switch (customerType) {
            case CHILD -> CHILD_DISCOUNT_VALUE;
            case STUDENT -> STUDENT_DISCOUNT_VALUE;
            default -> 0.0;
        };
    }

    Double getCustomerPlatformLoyaltyDiscount(Customer customer) {
        return customer.getOrders().size() % NUMBER_OF_ORDERS_TO_TRIGGER_PLATFORM_LOYALTY_DISCOUNT == 0
                ? PLATFORM_LOYALTY_DISCOUNT_VALUE
                : 0.0;
    }

    Double getCustomerRestaurantLoyaltyDiscount(Customer customer, Restaurant restaurant) {
        long ordersAtSameRestaurantCount = customer.getOrders()
                .stream()
                .filter(order -> order.getRestaurant().equals(restaurant))
                .count();

        return ordersAtSameRestaurantCount % NUMBER_OF_ORDERS_TO_TRIGGER_RESTAURANT_LOYALTY_DISCOUNT == 0
                ? RESTAURANT_LOYALTY_DISCOUNT_VALUE
                : 0.0;
    }
}
