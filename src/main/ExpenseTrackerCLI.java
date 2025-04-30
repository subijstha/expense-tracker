package main;

import model.Expense;
import persistence.ExpenseFileManager;
import service.ExpenseManager;
import util.InputValidator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerCLI {
    private final ExpenseManager manager;
    private final ExpenseFileManager fileManager;
    private final Scanner scanner;

    public ExpenseTrackerCLI(ExpenseManager manager, ExpenseFileManager fileManager) {
        this.manager = manager;
        this.fileManager = fileManager;
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        System.out.println("============= Welcome to Expense Tracker System =============");
        loadExpense();

        boolean running = true;

        while(running){
            printMenu();
            String choice = scanner.nextLine().trim();

            switch(choice){
                case "1":
                    addExpense();
                    break;
                case "2":
                    viewAllExpense();
                    break;
                case "3":
                    filterByCategory();
                    break;
                case "4":
                    viewTotalAmount();
                    break;
                case "5":
                    saveExpenses();
                    break;
                case "6":
                    running =false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again later.");
            }
        }


        System.out.println("GoodBye!!");
    }

    private void viewAllExpense() {
        List<Expense> expenses = manager.getAllExpenses();

        if(expenses.isEmpty()){
            System.out.println("No expenses found");
            return;
        }


        System.out.println("----All Expenses----");
        expenses.forEach(System.out::println);
    }

    private void filterByCategory(){
        System.out.println("Enter category to filter by: ");
        String category = scanner.nextLine().trim();

        List<Expense> filtered = manager.getExpensesByCategory(category);
        if(filtered.isEmpty()){
            System.out.println( "NO expenses found in this category");

        }else{
            System.out.println("---_Expenses in Category: " + "_---");
            filtered.forEach(System.out::println);
        }
    }

    private void viewTotalAmount(){
        double total = manager.getTotalAmount();
        System.out.printf("Total expenses: $%.2f\n", total);
    }


    private void saveExpenses(){
        try{
            fileManager.saveExpenses(manager.getAllExpenses());
            System.out.println("Expenses saved successfully");
        }catch (IOException e){
            System.out.println("Failed to save expenses: " + e.getMessage());
        }
    }

    private void loadExpense() {
        try{
            List<Expense> loadedExpenses = fileManager.loadExpenses();
            for(Expense e: loadedExpenses){
                manager.addExpense(e);
            }

            System.out.println("Loaded " + loadedExpenses.size());
        }catch(IOException e){
            System.out.println(" No previous data found, start again");
        }

    }

    private void addExpense() {
        System.out.println("Enter the expense details: ");

        String name = promptValidated("Name", InputValidator::isValidName);
        String category = promptValidated("Category", InputValidator::isValidCategory);
        String description = promptOptional("Description (optional)");
        String amountStr = promptValidated("Amount", InputValidator::isValidAmount);
        String dateStr = promptValidated("Date (yyyy-MM-dd)", InputValidator::isValidDate);

        double amount = Double.parseDouble(amountStr);
        LocalDate date = InputValidator.parseDate(dateStr);

        Expense expense = new Expense(name, category, description, amount, date);
        manager.addExpense(expense);

        System.out.println(" Expense sucessfully added ");
    }

    private String promptValidated(String name, java.util.function.Predicate<String> validator) {
        String data;
        do{
            System.out.println(name + ": " );

            data  = scanner.nextLine().trim();
            if(!validator.test(data)){
                System.out.println("Invalid " + name + ". Please try again later!!! ");
            }
        }while(!validator.test(data));
        return data;
    }

    private String promptOptional(String fileName){
        System.out.println(fileName + ": ");
        return scanner.nextLine().trim();
    }

    private void printMenu() {

                System.out.println("""
                
                -------- Menu --------
                1. Add Expense
                2. View All Expenses
                3. Filter by Category
                4. View Total Amount
                5. Save Expenses
                6. Exit
                ----------------------
                Enter your choice: """);
    }
}
