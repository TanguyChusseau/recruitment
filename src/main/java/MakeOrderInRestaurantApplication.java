import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import constants.Customers;
import constants.Meals;
import constants.Restaurants;
import constants.RestaurantOwners;
import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.user.customer.Customer;
import models.user.restaurantowner.RestaurantOwner;

import static constants.RestaurantMeals.*;

public class MakeOrderInRestaurantApplication {
    static Logger logger = Logger.getLogger(MakeOrderInRestaurantApplication.class.getName());

    public static void main(String[] args) {
        logger.info("Starting MakeOrderInRestaurantApplication.");

        logger.info("Setting up restaurants owners and meals...");
        setupRestaurantsOwnersAndMeals();

        logger.info("Setting up customers...");
        List<Customer> customers = setupCustomers();

        logger.info("Making orders...");
        makeOrders(customers);

        logger.info("Orders made successfully. Stopping MakeOrderInRestaurantApplication.");
    }

    private static void setupRestaurantsOwnersAndMeals() {
        RestaurantOwner robertDupont = new RestaurantOwner(
                RestaurantOwners.ROBERT_DUPONT.getFirstName(), RestaurantOwners.ROBERT_DUPONT.getLastName(),
                Restaurants.TICINO
        );
        RestaurantOwner magaliNoel = new RestaurantOwner(
                RestaurantOwners.MAGALI_NOEL.getFirstName(), RestaurantOwners.MAGALI_NOEL.getLastName(),
                Restaurants.ETOILE
        );
        RestaurantOwner nicolasBenoit = new RestaurantOwner(
                RestaurantOwners.NICOLAS_BENOIT.getFirstName(), RestaurantOwners.NICOLAS_BENOIT.getLastName(),
                Restaurants.TEXAN
        );

        for (Meal meal : TICINO_MEALS) robertDupont.addMeal(meal);
        for (Meal meal : ETOILE_MEALS) magaliNoel.addMeal(meal);
        for (Meal meal : TEXAN_MEALS) nicolasBenoit.addMeal(meal);

        robertDupont.updateMealPrice(Meals.PIZZA_TONNO, 15.0);
        robertDupont.checkMealPriceHistory(Meals.PIZZA_TONNO);
    }

    private static List<Customer> setupCustomers() {
        Customer catherineZwahlen = new Customer(
                Customers.CATHERINE_ZWAHLEN.getFirstName(),
                Customers.CATHERINE_ZWAHLEN.getLastName(),
                Customers.CATHERINE_ZWAHLEN.getType()
        );

        Customer clementineDelerce = new Customer(
                Customers.CLEMENTINE_DELERCE.getFirstName(),
                Customers.CLEMENTINE_DELERCE.getLastName(),
                Customers.CLEMENTINE_DELERCE.getType()
        );

        return List.of(catherineZwahlen, clementineDelerce);
    }

    private static void makeOrders(List<Customer> customers) {
        Customer catherineZwahlen = customers.get(0);
        Customer clementineDelerce = customers.get(1);

        catherineZwahlen.makeOrder(Restaurants.TICINO, List.of(Meals.PIZZA_TONNO, Meals.TIRAMISU));

        logger.info("Looking for vegetarian restaurants...");
        List<Restaurant> vegetarianRestaurants = clementineDelerce.getVegetarianRestaurants(
                List.of(Restaurants.TICINO, Restaurants.TEXAN, Restaurants.ETOILE)
        );

        logger.info("Making orders for vegetarian restaurants...");
        Map<Restaurant, List<Meal>> restaurantsAndMeals = new HashMap<>();
        vegetarianRestaurants.forEach(restaurant -> restaurantsAndMeals.put(restaurant, restaurant.getMeals()));

        clementineDelerce.makeOrders(restaurantsAndMeals);
    }
}