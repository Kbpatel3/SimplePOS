package repository;

import model.Item;
import model.PaymentMethod;
import model.Transaction;
import model.TransactionItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionRepository implements TransactionRepository {
    /** Database connection URL */
    private static final String URL = "jdbc:postgresql://localhost:5432/pos";

    /** Database connection credentials: Username */
    private static final String USER = "user";

    /** Database connection credentials: Password */
    private static final String PASSWORD = "password";

    /**
     * Save a transaction to the DB
     *
     * @param transaction the transaction to save
     */
    @Override
    public void save(Transaction transaction) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL query to insert transaction into database
            String query = "INSERT INTO transactions (total_amount, total_tax, ebt_total, " +
                    "non_ebt_total, payment_method) VALUES (?, ?, ?, ?, ?)";

            // Prepare the query
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                // Set the parameters
                statement.setDouble(1, transaction.getTotalAmount());
                statement.setDouble(2, transaction.getTotalTax());
                statement.setDouble(3, transaction.getEbtTotal());
                statement.setDouble(4, transaction.getNonEbtTotal());
                statement.setString(5, transaction.getPaymentMethod().toString());

                // Execute the query
                statement.executeUpdate();

                // Get the generated key
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long transactionId = generatedKeys.getLong(1);
                    this.saveTransactionItems(transaction.getItems(), transactionId, connection);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void saveTransactionItems(List<TransactionItem> items, long transactionId,
                                      Connection connection) throws SQLException {
        String query = "INSERT INTO transaction_items (transaction_id, item_id, quantity, " +
                "subtotal) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (TransactionItem item : items) {
                statement.setLong(1, transactionId);
                statement.setInt(2, item.getItem().getItemId());
                statement.setInt(3, item.getQuantity());
                statement.setDouble(4, item.getSubtotal());
                statement.executeUpdate();
            }
            statement.executeBatch();
        }
    }

    /**
     * Retrieve transactions from the DB within a date range
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return a list of transactions within the date range
     */
    @Override
    public List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL query to retrieve transactions from database
            String query = "SELECT * FROM transactions WHERE DATE(transaction_date) BETWEEN ? " +
                    "AND ?";

            // Prepare the query
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set the parameters
                statement.setDate(1, Date.valueOf(startDate));
                statement.setDate(2, Date.valueOf(endDate));

                // Execute the query
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        Transaction transaction = this.mapRowToTransaction(rs);
                        transactions.add(transaction);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return transactions;
    }

    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException {
        int transactionId = rs.getInt("transaction_id");
        Timestamp transactionDate = rs.getTimestamp("transaction_date");
        String paymentMethod = rs.getString("payment_method");
        double totalAmount = rs.getDouble("total_amount");
        double totalTax = rs.getDouble("total_tax");
        double ebtTotal = rs.getDouble("ebt_total");
        double nonEbtTotal = rs.getDouble("non_ebt_total");

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setTransactionDate(transactionDate.toLocalDateTime());
        transaction.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
        transaction.setTotalAmount(totalAmount);
        transaction.setTotalTax(totalTax);
        transaction.setEbtTotal(ebtTotal);
        transaction.setNonEbtTotal(nonEbtTotal);

        transaction.setItems(this.findItemsByTransactionId(transactionId));

        return transaction;

    }

    private List<TransactionItem> findItemsByTransactionId(int transactionId) {
        List<TransactionItem> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM transaction_items WHERE transaction_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, transactionId);

                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        TransactionItem item = this.mapRowToTransactionItem(rs);
                        items.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return items;
    }

    private TransactionItem mapRowToTransactionItem(ResultSet rs) throws SQLException {
        int transactionItemId = rs.getInt("transaction_item_id");
        int itemId = rs.getInt("item_id");
        int quantity = rs.getInt("quantity");
        double subtotal = rs.getDouble("subtotal");

        TransactionItem item = new TransactionItem();
        item.setTransactionItemId(transactionItemId);
        item.setTransactionId(rs.getInt("transaction_id"));
        item.setItemId(itemId);
        item.setQuantity(quantity);
        item.setSubtotal(subtotal);

        item.setItem(this.findItemById(itemId));

        return item;
    }

    private Item findItemById(int itemId) {
        String query = "SELECT * FROM items WHERE item_id = ?";
        Item item = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, itemId);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        item = this.mapRowToItem(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return item;
    }

    private Item mapRowToItem(ResultSet rs) throws SQLException {
        int itemId = rs.getInt("itemid");
        String name = rs.getString("name");
        String barcode = rs.getString("barcode");
        double price = rs.getDouble("price");
        double taxRate = rs.getDouble("tax_rate");
        boolean isEbtEligible = rs.getBoolean("is_ebt_eligible");


        Item item = new Item();

        item.setBarcode(barcode);
        item.setName(name);
        item.setPrice(price);
        item.setTaxRate(taxRate);
        item.setEbtEligible(isEbtEligible);
        item.setItemId(itemId);

        return item;
    }
}
