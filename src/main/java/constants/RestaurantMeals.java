package constants;

import models.restaurant.Meal;

import java.util.List;

public final class RestaurantMeals {
    public static final List<Meal> TICINO_MEALS = List.of(Meals.PIZZA_TONNO, Meals.PASTA_BOLOGNESE, Meals.TIRAMISU);

    public static final List<Meal> ETOILE_MEALS = List.of(Meals.CASSOULET, Meals.RISOTTO, Meals.BANANA_SPLIT);

    public static final List<Meal> TEXAN_MEALS = List.of(Meals.BURGER_VEGE, Meals.FAJITAS);

    private RestaurantMeals() {}
}
