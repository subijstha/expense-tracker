package gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.Expense;
import service.ExpenseManager;

public class ExpenseTableView {

    private final ExpenseManager manager;
    private final ObservableList<Expense> expenseList = FXCollections.observableArrayList();
    private final TableView<Expense> tableView = new TableView<>();
    private final Label totalLabel = new Label();

    public ExpenseTableView(ExpenseManager manager) {
        this.manager = manager;
        setupTable();
        refreshTable();
    }

    public Node getView() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));
        pane.setCenter(tableView);
        pane.setBottom(totalLabel);
        BorderPane.setMargin(totalLabel, new Insets(10, 0, 0, 0));
        return pane;
    }

    private void setupTable() {
        TableColumn<Expense, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Expense, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Expense, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Expense, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Expense, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(categoryCol);
        tableView.getColumns().add(descriptionCol);
        tableView.getColumns().add(amountCol);
        tableView.getColumns().add(dateCol);
        //tableView.getColumns().addAll(nameCol, categoryCol, descriptionCol, amountCol, dateCol);
        tableView.setItems(expenseList);
    }

    public void refreshTable() {
        expenseList.setAll(manager.getAllExpenses());
        updateTotal();
    }

    private void updateTotal() {
        double total = manager.getTotalAmount();
        totalLabel.setText(String.format("Total: $%.2f", total));
    }
}
