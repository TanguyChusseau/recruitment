package constants;

import models.restaurant.Meal;
import models.restaurant.MealType;

public final class Meals {
    public static final Meal PIZZA_TONNO = new Meal(Restaurants.TICINO, "Pizza tonno", MealType.CLASSIC, 20.50);
    public static final Meal PASTA_BOLOGNESE = new Meal(Restaurants.TICINO, "Pasta bolognese", MealType.CLASSIC, 18.30);
    public static final Meal TIRAMISU = new Meal(Restaurants.TICINO, "Tiramisu",MealType.VEGETARIAN, 12.80);

    public static final Meal CASSOULET = new Meal(Restaurants.ETOILE, "Cassoulet", MealType.CLASSIC, 22.60);
    public static final Meal RISOTTO = new Meal(Restaurants.ETOILE, "Risotto", MealType.VEGETARIAN,19.15);
    public static final Meal BANANA_SPLIT = new Meal(Restaurants.ETOILE, "Banana split", MealType.VEGETARIAN, 14.90);

    public static final Meal BURGER_VEGE = new Meal(Restaurants.TEXAN, "Burger vege", MealType.VEGETARIAN, 21.10);
    public static final Meal FAJITAS = new Meal(Restaurants.TEXAN, "Fajitas", MealType.CLASSIC, 24.0);

    private Meals() {}
}
