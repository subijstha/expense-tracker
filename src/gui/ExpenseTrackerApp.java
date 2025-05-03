package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Expense;
import persistence.ExpenseFileManager;
import service.ExpenseManager;

import java.io.IOException;
import java.util.List;

public class ExpenseTrackerApp extends Application {
    private ExpenseManager manager;
    private ExpenseFileManager fileManager;



private ExpenseTableView tableView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        manager = new ExpenseManager();
        fileManager = new ExpenseFileManager("expense.json");

        loadExpensesFromFile();

        tableView = new ExpenseTableView(manager);
        ExpenseForm form = new ExpenseForm(manager, tableView);

        BorderPane root = new BorderPane();
        root.setTop(form.getView());
        root.setCenter(tableView.getView());

        Scene scene = new Scene(root,800, 500);
        primaryStage.setTitle("Expense Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> saveExpensesToFile());

    }

    private void loadExpensesFromFile() {
        try{
            List<Expense> expenses = fileManager.loadExpenses();
            expenses.forEach(manager:: addExpense);
         }catch(IOException e){
            System.out.println("COuldnot load expenses: " + e.getMessage());
        }

    }

    private void saveExpensesToFile(){
        try{
            fileManager.saveExpenses(manager.getAllExpenses());
        }catch(IOException e){
            System.out.println("Failed to save the expenses: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
