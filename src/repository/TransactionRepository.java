package repository;

import model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {
    /**
     * Save a transaction to the DB
     * @param transaction the transaction to save
     */
    void save(Transaction transaction);

    /**
     * Retrieve transactions from the DB within a date range
     * @param startDate the start date
     * @param endDate the end date
     * @return a list of transactions within the date range
     */
    List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate);
}
