package repository;

import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcItemRepository implements ItemRepository {
    /** Database connection URL */
    private static final String URL = "jdbc:postgresql://localhost:5432/pos";

    /** Database connection credentials: Username */
    private static final String USER = "user";

    /** Database connection credentials: Password */
    private static final String PASSWORD = "password";

    /**
     * Save item to database
     *
     * @param item item to save
     */
    @Override
    public void save(Item item) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL query to insert item into database
            String query = "INSERT INTO items (name, barcode, price, tax_rate, ebt_eligible) " +
                    "VALUES (?, ?, ?, ?, ?)";

            // Prepare the query
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                // Set the parameters
                statement.setString(1, item.getName());
                statement.setString(2, item.getBarcode());
                statement.setDouble(3, item.getPrice());
                statement.setDouble(4, item.getTaxRate());
                statement.setBoolean(5, item.isEbtEligible());

                // Execute the query
                statement.executeUpdate();

                // Get the generated key
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        item.setItemId(generatedKeys.getInt(1));
                    }
                }

            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Delete item from database
     *
     * @param barcode barcode of item to delete
     */
    @Override
    public void delete(String barcode) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL query to delete item from database
            String query = "DELETE FROM items WHERE barcode = ?";

            // Prepare the query
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set the parameter
                statement.setString(1, barcode);

                // Execute the query
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Update item in database
     *
     * @param item item to update
     */
    @Override
    public void update(Item item) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL query to update item in database
            String query = "UPDATE items SET name = ?, price = ?, tax_rate = ?, ebt_eligible = ? WHERE barcode = ?";

            // Prepare the query
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set the parameters
                statement.setString(1, item.getName());
                statement.setDouble(2, item.getPrice());
                statement.setDouble(3, item.getTaxRate());
                statement.setBoolean(4, item.isEbtEligible());
                statement.setString(5, item.getBarcode());

                // Execute the query
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Find item by barcode
     *
     * @param barcode barcode of item to find
     * @return item with the barcode
     */
    @Override
    public Item findByBarcode(String barcode) {
        Item item = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL query to find item by barcode
            String query = "SELECT * FROM items WHERE barcode = ?";

            // Prepare the query
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set the parameter
                statement.setString(1, barcode);

                // Get the resultSet
                try (ResultSet resultSet = statement.executeQuery()) {
                    // If the resultSet has a next row
                    if (resultSet.next()) {
                        // Create a new item
                        item = new Item();

                        // Set the item properties
                        item.setName(resultSet.getString("name"));
                        item.setBarcode(resultSet.getString("barcode"));
                        item.setPrice(resultSet.getDouble("price"));
                        item.setTaxRate(resultSet.getDouble("tax_rate"));
                        item.setEbtEligible(resultSet.getBoolean("ebt_eligible"));
                        item.setItemId(resultSet.getInt("itemid"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return item;
    }

    /**
     * Find all items in database
     *
     * @return list of all items
     */
    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL query to find all items
            String query = "SELECT * FROM items";

            // Prepare the query
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Get the resultSet
                try (ResultSet resultSet = statement.executeQuery()) {
                    // While the resultSet has a next row
                    while (resultSet.next()) {
                        // Create a new item
                        Item item = new Item();

                        // Set the item properties
                        item.setName(resultSet.getString("name"));
                        item.setBarcode(resultSet.getString("barcode"));
                        item.setPrice(resultSet.getDouble("price"));
                        item.setTaxRate(resultSet.getDouble("tax_rate"));
                        item.setEbtEligible(resultSet.getBoolean("ebt_eligible"));

                        // Add the item to the list
                        items.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return items;
    }
}
