package service;

import model.PaymentMethod;
import model.Transaction;
import repository.TransactionRepository;
import model.TransactionItem;
import model.Item;

import java.time.LocalDate;
import java.util.List;

public class TransactionService {
    /** The transaction repository */
    private TransactionRepository transactionRepository;

    /** The item service */
    private ItemService itemService;

    /**
     * Constructor
     * @param transactionRepository The transaction repository
     * @param itemService The item service
     */
    public TransactionService(TransactionRepository transactionRepository, ItemService itemService) {
        this.transactionRepository = transactionRepository;
        this.itemService = itemService;
    }

    /**
     * Process a transaction
     * @param items The items belonging to the transaction
     * @param paymentMethod The payment method used for the transaction
     */
    public void processTransaction(List<TransactionItem> items, PaymentMethod paymentMethod) {
        Transaction transaction = new Transaction(items, paymentMethod);
        transactionRepository.save(transaction);
    }

    /**
     * Get all transactions for a given date range
     * @param startDate The start date
     * @param endDate The end date
     * @return The list of transactions
     */
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByDateRange(startDate, endDate);
    }

    /**
     * Create a transaction item
     * @param barcode The barcode of the item
     * @param quantity The quantity of the item being purchased
     * @return The transaction item
     */
    public TransactionItem createTransactionItem(String barcode, int quantity) {
        Item item = itemService.getItemByBarcode(barcode);
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }
        return new TransactionItem(item, quantity);
    }

    /**
     * Checkout with EBT
     * @param items The items to checkout
     */
    public void checkoutWithEbt(List<TransactionItem> items) {
        List<TransactionItem> ebtItems = items.stream()
                .filter(item -> item.getItem().isEbtEligible())
                .toList();

        List<TransactionItem> nonEbtItems = items.stream()
                .filter(item -> !item.getItem().isEbtEligible())
                .toList();

        Transaction ebtTransaction = new Transaction(ebtItems, PaymentMethod.EBT);
        Transaction nonEbtTransaction = new Transaction(nonEbtItems, PaymentMethod.CASH);

        transactionRepository.save(ebtTransaction);
        transactionRepository.save(nonEbtTransaction);
    }

    /**
     * Checkout with cash or card
     * @param items The items to checkout
     * @param paymentMethod The payment method
     */
    public void checkoutWithCashOrCard(List<TransactionItem> items, PaymentMethod paymentMethod) {
        Transaction transaction = new Transaction(items, paymentMethod);
        transactionRepository.save(transaction);
    }
}
