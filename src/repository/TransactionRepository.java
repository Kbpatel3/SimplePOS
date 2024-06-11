package repository;

import model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate);
}
