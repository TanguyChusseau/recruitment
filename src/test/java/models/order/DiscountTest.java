package models.order;

import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.user.customer.Customer;
import models.user.customer.CustomerType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountTest {

    @Test
    void getCustomerTypeAssociatedDiscount_whenChild_thenChildDiscount() {
        // Given
        CustomerType customerType = CustomerType.CHILD;

        // When
        double expectedChildDiscount = Discount.CHILD_DISCOUNT_VALUE;
        double actualDiscount = new Discount().getCustomerTypeAssociatedDiscount(customerType);

        // Then
        assertEquals(expectedChildDiscount, actualDiscount);
    }

    @Test
    void getCustomerTypeAssociatedDiscount_whenStudent_thenChildDiscount() {
        // Given
        CustomerType customerType = CustomerType.STUDENT;

        // When
        double expectedChildDiscount = Discount.STUDENT_DISCOUNT_VALUE;
        double actualDiscount = new Discount().getCustomerTypeAssociatedDiscount(customerType);

        // Then
        assertEquals(expectedChildDiscount, actualDiscount);
    }

    @Test
    void getCustomerTypeAssociatedDiscount_whenOther_thenNoDiscount() {
        // Given
        CustomerType customerType = CustomerType.OTHER;

        // When
        double expectedChildDiscount = 0.0;
        double actualDiscount = new Discount().getCustomerTypeAssociatedDiscount(customerType);

        // Then
        assertEquals(expectedChildDiscount, actualDiscount);
    }

    @Test
    void getCustomerPlatformLoyaltyDiscount_whenCustomerHasOrdersCountNotEqualToMultipleOfTenOrders_thenNoDiscount() {
        // Given
        Customer customer = new Customer("John", "Child", CustomerType.CHILD);
        Restaurant dominos = new Restaurant("Dominos");
        Meal pizza = new Meal(dominos, "Meal", 10.0);

        Order order10 = new Order(dominos, customer, List.of(pizza));
        customer.addOrder(order10);

        // When
        double expectedDiscount = 0.0;
        double actualDiscount = new Discount().getCustomerPlatformLoyaltyDiscount(customer);

        // Then
        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    void getCustomerPlatformLoyaltyDiscount_whenCustomerHasOrdersCountEqualToMultipleOfTenOrders_thenPlatformDiscount() {
        // Given
        Customer customer = new Customer("John", "Child", CustomerType.CHILD);
        Restaurant dominos = new Restaurant("Dominos");
        Meal pizza = new Meal(dominos, "Meal", 10.0);

        List<Order> customerOrders = generateCustomerOrders(dominos, customer, pizza);
        customerOrders.forEach(customer::addOrder);

        Order order10 = new Order(dominos, customer, List.of(pizza));
        customer.addOrder(order10);

        // When
        double expectedDiscount = Discount.PLATFORM_LOYALTY_DISCOUNT_VALUE;
        double actualDiscount = new Discount().getCustomerPlatformLoyaltyDiscount(customer);

        // Then
        assertEquals(expectedDiscount, actualDiscount);
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
