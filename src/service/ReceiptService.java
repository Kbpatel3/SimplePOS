package service;

import model.Transaction;
import model.TransactionItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReceiptService {
    private Connection connection;

    public ReceiptService(Connection connection) {
        this.connection = connection;
    }

    public void saveReceipt(int transactionId, String receiptContent) {
        String query = "INSERT INTO Receipts (TransactionID, ReceiptContent) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, transactionId);
            stmt.setString(2, receiptContent);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String generateReceiptContent(Transaction transaction) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Receipt\n");
        receipt.append("Transaction ID: ").append(transaction.getTransactionId()).append("\n");
        receipt.append("Date: ").append(transaction.getTransactionDate()).append("\n");
        receipt.append("Items:\n");
        for (TransactionItem item : transaction.getItems()) {
            receipt.append(item.getItem().getName()).append(" x").append(item.getQuantity())
                    .append(" @ $").append(item.getItem().getPrice()).append(" = $")
                    .append(item.getSubtotal()).append("\n");
        }
        receipt.append("Total Tax: $").append(transaction.getTotalTax()).append("\n");
        receipt.append("Total Amount: $").append(transaction.getTotalAmount()).append("\n");
        return receipt.toString();
    }
}

