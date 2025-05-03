package gui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Expense;
import service.ExpenseManager;
import util.InputValidator;

import java.time.LocalDate;

public class ExpenseForm {

    private final ExpenseManager manager;
    private final ExpenseTableView tableView;

    private final TextField nameField = new TextField();
    private final TextField categoryField = new TextField();
    private final TextField descriptionField = new TextField();
    private final TextField amountField = new TextField();
    private final DatePicker datePicker = new DatePicker(LocalDate.now());
    private final Button addButton = new Button("Add Expense");

    public ExpenseForm(ExpenseManager manager, ExpenseTableView tableView) {
        this.manager = manager;
        this.tableView = tableView;
        setupAddButton();
    }

    public Node getView() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Category:"), 0, 1);
        form.add(categoryField, 1, 1);
        form.add(new Label("Description:"), 0, 2);
        form.add(descriptionField, 1, 2);
        form.add(new Label("Amount:"), 0, 3);
        form.add(amountField, 1, 3);
        form.add(new Label("Date:"), 0, 4);
        form.add(datePicker, 1, 4);
        form.add(addButton, 1, 5);

        return form;
    }

    private void setupAddButton() {
        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            String description = descriptionField.getText().trim();
            String amountText = amountField.getText().trim();
            LocalDate date = datePicker.getValue();

            if (!InputValidator.isValidName(name) || !InputValidator.isValidCategory(category) ||
                    !InputValidator.isValidAmount(amountText) || date == null) {
                showAlert("Invalid input", "Please check all fields and try again.");
                return;
            }

            double amount = Double.parseDouble(amountText);
            Expense expense = new Expense(name, category, description, amount, date);
            manager.addExpense(expense);
            tableView.refreshTable();

            clearForm();
        });
    }

    private void clearForm() {
        nameField.clear();
        categoryField.clear();
        descriptionField.clear();
        amountField.clear();
        datePicker.setValue(LocalDate.now());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
