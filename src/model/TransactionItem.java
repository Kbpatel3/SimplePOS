package model;

public class TransactionItem {
    /** Represents an item in a transaction */
    private Item item;

    /** Represents the quantity of the item */
    private int quantity;

    /**
     * Constructs a TransactionItem object with the specified item and quantity
     * @param item the item
     * @param quantity the quantity
     */
    public TransactionItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
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
}
