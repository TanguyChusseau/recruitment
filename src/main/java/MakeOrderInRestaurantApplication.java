import java.util.List;
import java.util.logging.Logger;

import constants.Customers;
import constants.Meals;
import constants.Restaurants;
import constants.RestaurantOwners;
import models.restaurant.Meal;
import models.user.customer.Customer;
import models.user.restaurantowner.RestaurantOwner;

import static constants.RestaurantMeals.*;

public class MakeOrderInRestaurantApplication {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(MakeOrderInRestaurantApplication.class.getName());
        logger.info("Starting MakeOrderInRestaurantApplication.");

        logger.info("Setting up restaurants with owner and meals...");
        setupRestaurantsWithOwnerAndMeals();

        logger.info("Setting up customers...");
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

        logger.info("Making orders...");
        catherineZwahlen.makeOrder(Restaurants.TICINO, List.of(Meals.PIZZA_TONNO, Meals.TIRAMISU));
        clementineDelerce.makeOrder(Restaurants.ETOILE, List.of(Meals.RISOTTO, Meals.BANANA_SPLIT));

        logger.info("Orders made successfully. Stopping MakeOrderInRestaurantApplication.");
    }

    private static void setupRestaurantsWithOwnerAndMeals() {
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
    }
}