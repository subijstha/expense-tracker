package main;

import persistence.ExpenseFileManager;
import service.ExpenseManager;

public class App {
    public static void main(String[] args){
        String filePath = "expenses.json";

        ExpenseManager manager = new ExpenseManager();
        ExpenseFileManager fileManager = new ExpenseFileManager(filePath);

        ExpenseTrackerCLI  cli = new ExpenseTrackerCLI(manager, fileManager);
        cli.start();
    }
}
