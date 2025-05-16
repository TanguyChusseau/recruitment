package models.restaurant;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import models.Entity;

import static java.time.LocalDateTime.now;

public class Meal implements Entity {
    @Getter
    private final Restaurant restaurant;

    @Getter
    private final String name;

    @Getter
    private final MealType type;

    @Getter
    private Double price;

    private final Map<LocalDateTime, Double> priceHistory;

    public Meal(Restaurant restaurant, String name, MealType type, Double price) {
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
        this.type = type;
        this.priceHistory = new LinkedHashMap<>();
        this.priceHistory.put(now(), price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Meal meal = (Meal) o;
        return Objects.equals(name, meal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Map<LocalDateTime, Double> getPriceHistory() {
        return Collections.unmodifiableMap(this.priceHistory);
    }

    public void updatePrice(Double newPrice) {
        this.price = newPrice;
        this.priceHistory.put(now(), newPrice);
    }
}