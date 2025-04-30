package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

import java.util.Objects;

public class Expense {
    public String name;
    public LocalDate date;
    public String category;
    public double amount;

    public String description;



    @JsonCreator
    public Expense( @JsonProperty("name") String name,
                    @JsonProperty("category") String category,
                    @JsonProperty("description") String description,
                    @JsonProperty("amount") double amount,
                    @JsonProperty("date") LocalDate date){
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty. ");
        if(category == null || category.isBlank()) throw new IllegalArgumentException("Category cannot be empty. ");
        if(amount < 0) throw new IllegalArgumentException("Amount cannot be negative. ");
        if(date == null) throw new IllegalArgumentException("Date cannnot be null");

        this.name =name;
        this.category =category;
        this.description =description;
        this.amount = amount;
        this.date = date;

    }
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }


    @Override
    public String toString() {
        return String.format("[%s] %-15s | %-10s | %-30s | $%.2f",
                date, name, category, description, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;
        Expense expense = (Expense) o;
        return Double.compare(expense.amount, amount) == 0 &&
                name.equals(expense.name) &&
                category.equals(expense.category) &&
                description.equals(expense.description) &&
                date.equals(expense.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, description, amount, date);
    }
}

