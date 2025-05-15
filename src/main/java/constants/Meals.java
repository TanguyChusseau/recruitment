package constants;

import models.restaurant.Meal;

public final class Meals {
    public static final Meal PIZZA_TONNO = new Meal(Restaurants.TICINO, "Pizza tonno", 20.50);
    public static final Meal PASTA_BOLOGNESE = new Meal(Restaurants.TICINO, "Pasta bolognese", 18.30);
    public static final Meal TIRAMISU = new Meal(Restaurants.TICINO, "Tiramisu", 12.80);

    public static final Meal CASSOULET = new Meal(Restaurants.ETOILE, "Cassoulet", 22.60);
    public static final Meal RISOTTO = new Meal(Restaurants.ETOILE, "Risotto", 19.15);
    public static final Meal BANANA_SPLIT = new Meal(Restaurants.ETOILE, "Banana split", 14.90);

    public static final Meal BURGER_VEGE = new Meal(Restaurants.TEXAN, "Burger vege", 21.10);
    public static final Meal FAJITAS = new Meal(Restaurants.TEXAN, "Fajitas", 24.0);

    private Meals() {}
}
