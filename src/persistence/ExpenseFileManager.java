package persistence;

import model.Expense;
import util.JsonUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class ExpenseFileManager {
    private final Path filePath;


    //comment: Initializes the file manager with a given file name
    public ExpenseFileManager(String filePath){
        this.filePath = Paths.get(filePath);
    }


    public void saveExpenses(List<Expense> expenses) throws IOException{
        String json = JsonUtil.toJson(expenses);
        Files.writeString(filePath, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }


    public List<Expense> loadExpenses() throws IOException{
        if(!Files.exists(filePath)){
            return List.of();
        }

        String json = Files.readString(filePath);
        return JsonUtil.fromJsonToList(json, Expense.class);
    }





}
