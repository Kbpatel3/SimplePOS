package model;

public class TransactionItem {
    /** Represents the transactionItem ID */
    private int transactionItemId;

    /** Represents the transaction ID */
    private int transactionId;

    /** Represents the Item ID */
    private String itemId;

    /** Represents the quantity of the item */
    private int quantity;

    /** Represents the subtotal of the item */
    private double subtotal;

    /** Represents an item in a transaction */
    private Item item;


    /**
     * Constructs a TransactionItem object with the specified item and quantity
     * @param item the item
     * @param quantity the quantity
     */
    public TransactionItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.itemId = item.getBarcode();
        this.subtotal = item.getPrice() * quantity;
    }

    /**
     * Constructs a TransactionItem object with no parameters
     */
    public TransactionItem() {
        this.item = null;
        this.quantity = 0;
        this.itemId = "";
        this.subtotal = 0;
    }

    /**
     * Returns the item
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the item
     * @param item the item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Returns the quantity
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the transactionItem ID
     * @return the transactionItem ID
     */
    public int getTransactionItemId() {
        return transactionItemId;
    }

    /**
     * Sets the transactionItem ID
     * @param transactionItemId the transactionItem ID
     */
    public void setTransactionItemId(int transactionItemId) {
        this.transactionItemId = transactionItemId;
    }

    /**
     * Returns the transaction ID
     * @return the transaction ID
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the transaction ID
     * @param transactionId the transaction ID
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Returns the item ID
     * @return the item ID
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets the item ID
     * @param itemId the item ID
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the subtotal
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Sets the subtotal
     * @param subtotal the subtotal
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
