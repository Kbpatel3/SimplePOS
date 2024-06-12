import model.Item;
import model.PaymentMethod;
import model.TransactionItem;
import repository.ItemRepository;
import repository.JdbcItemRepository;
import repository.JdbcTransactionRepository;
import repository.TransactionRepository;
import service.ItemService;
import service.ReceiptService;
import service.TransactionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Hello, World!");


        // Instantiate the repositories
        TransactionRepository transactionRepository = new JdbcTransactionRepository();
        ItemRepository itemRepository = new JdbcItemRepository();

        // Instantiate the services
        ItemService itemService = new ItemService(itemRepository);
        TransactionService transactionService = new TransactionService(transactionRepository,
                itemService);

        // Test the services
        System.out.println("Adding items to the database...");
        itemService.addItem(new Item("Coke 12oz", "0001", 1.25, 0.07, true));
        itemService.addItem(new Item("Pepsi 12oz", "0002", 1.25, 0.07, true));
        itemService.addItem(new Item("Malboro", "0003", 6.25, 0.07, false));
        itemService.addItem(new Item("Camel", "0004", 6.25, 0.07, false));
        itemService.addItem(new Item("Gas", "0005", 2.25, 0.0, false));
        itemService.addItem(new Item("Cigar", "0006", 1.19, 0.07, false));
        itemService.addItem(new Item("Black and Mild", "0007", 0.89, 0.07, false));
        System.out.println("Items added to the database.");

        // View all items in the database
        for (Item item : itemService.getAllItems()) {
            System.out.println(item.getName());
        }

        // Retrieve an item by its id
        System.out.println("Retrieving items by barcode...");
        System.out.println(itemService.getItemByBarcode("0001").getName());
        System.out.println(itemService.getItemByBarcode("0004").getName());

        // Remove an item by its id
        System.out.println("Removing an item by barcode...");
        itemService.removeItem("0005");

        // View all items in the database
        System.out.println("Items in the database:");
        for (Item item : itemService.getAllItems()) {
            System.out.println(item.getName());
        }

        // Update an item's price
        System.out.println("Updating an item's price...");
        itemService.UpdateItemPrice("0001", 1.50);

        // View all items in the database
        System.out.println("Items in the database:");
        for (Item item : itemService.getAllItems()) {
            System.out.println(item.getName() + " " + item.getPrice());
        }

        TransactionItem item1 = transactionService.createTransactionItem("0006", 2);
        TransactionItem item2 = transactionService.createTransactionItem("0007", 1);

        transactionService.processTransaction(List.of(item1, item2), PaymentMethod.CASH);
    }
}
