package models.user.restaurantowner;

import lombok.Getter;
import models.restaurant.Meal;
import models.restaurant.Restaurant;
import models.user.User;
import services.restaurant.AbstractRestaurantService;
import services.restaurant.RestaurantService;

public class RestaurantOwner implements User {

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    private final Restaurant restaurant;

    private final AbstractRestaurantService restaurantService;

    public RestaurantOwner(String firstName, String lastName, Restaurant restaurant) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.restaurant = restaurant;
        this.restaurantService = new RestaurantService();
    }

    public void addMeal(Meal meal) {
        this.restaurantService.addMealToRestaurant(this.restaurant, meal);
    }
}