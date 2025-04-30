package service;

import model.Expense;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseManager {
    private final List<Expense> expenses;

    public ExpenseManager(){
        this.expenses = new ArrayList<>();

    }
/*

    @param expense the expense to add
    @throws IllegalArgumentException if the expense is null

*/

    public void addExpense(Expense expense){
        if(expense == null){
            throw new IllegalArgumentException("Expense cant be null");

        }
        expenses.add(expense);
    }

    public boolean removeExpense(Expense expense){
        return expenses.remove(expense);
    }

    public List<Expense> getAllExpenses(){
        return Collections.unmodifiableList(expenses);
    }

    public List<Expense> getExpensesByCategory(String category){
        return expenses.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Expense> getExpensesByDate(LocalDate date){
        return expenses.stream()
                .filter(e -> e.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Expense> getExpenseBetween(LocalDate start, LocalDate end){
        return expenses.stream()
                .filter(e-> !e.getDate().isBefore(start) && !e.getDate().isAfter(end))
                .collect(Collectors.toList());

    }

    public double getTotalAmount(){
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Map<String, List<Expense>> getByCategory(){
        return expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory));
    }

    public void clearAllExpense(){
        expenses.clear();
    }

    public boolean isEmpty(){
        return expenses.isEmpty();
    }
}


