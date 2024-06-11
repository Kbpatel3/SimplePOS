package model;

import java.util.List;

public class Transaction {
    /** List of items in the transaction */
    private List<TransactionItem> items;

    /** Payment method used for the transaction */
    private PaymentMethod paymentMethod;

    /** Total amount of the transaction */
    private double totalAmount;

    /** Total tax of the transaction */
    private double totalTax;

    /** Total amount of EBT items in the transaction */
    private double ebtTotal;

    /** Total amount of non-EBT items in the transaction */
    private double nonEbtTotal;

    /**
     * Constructor for Transaction
     * @param items List of items in the transaction
     * @param paymentMethod Payment method used for the transaction
     */
    public Transaction(List<TransactionItem> items, PaymentMethod paymentMethod) {
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.calculateTotals();
    }

    /**
     * Calculates the total amount, total tax, EBT total, and non-EBT total of the transaction
     */
    private void calculateTotals() {
        // Set all totals to 0
        this.totalAmount = 0;
        this.totalTax = 0;
        this.ebtTotal = 0;
        this.nonEbtTotal = 0;

        // Loop through all items in the transaction and calculate totals
        for (TransactionItem item : this.items) {
            // Get the total amount of the item (price * quantity)
            double itemTotal = item.getItem().getPrice() * item.getQuantity();

            if (paymentMethod == PaymentMethod.EBT) {
                if (item.getItem().isEbtEligible()) {
                    this.ebtTotal += itemTotal;
                } else {
                    // Calculate tax for non-EBT items
                    double tax = itemTotal * item.getItem().getTaxRate();
                    this.totalTax += tax;
                    this.nonEbtTotal += itemTotal + tax;
                }
            } else {
                // Calculate tax for all items
                double tax = itemTotal * item.getItem().getTaxRate();
                this.totalTax += tax;
                this.totalAmount += itemTotal + tax;
            }
        }

        if (paymentMethod == PaymentMethod.EBT) {
            this.totalAmount = this.ebtTotal + this.nonEbtTotal;
        } else {
            this.totalAmount += this.totalTax;
        }
    }

    /**
     * Get the list of items in the transaction
     * @return List of items in the transaction
     */
    public List<TransactionItem> getItems() {
        return items;
    }

    /**
     * Set the list of items in the transaction
     * @param items List of items in the transaction
     */
    public void setItems(List<TransactionItem> items) {
        this.items = items;
    }

    /**
     * Get the payment method used for the transaction
     * @return Payment method used for the transaction
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Set the payment method used for the transaction
     * @param paymentMethod Payment method used for the transaction
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Get the total amount of the transaction
     * @return Total amount of the transaction
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Set the total amount of the transaction
     * @param totalAmount Total amount of the transaction
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Get the total tax of the transaction
     * @return Total tax of the transaction
     */
    public double getTotalTax() {
        return totalTax;
    }

    /**
     * Set the total tax of the transaction
     * @param totalTax Total tax of the transaction
     */
    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    /**
     * Get the total amount of EBT items in the transaction
     * @return
     */
    public double getEbtTotal() {
        return ebtTotal;
    }

    /**
     * Set the total amount of EBT items in the transaction
     * @param ebtTotal Total amount of EBT items in the transaction
     */
    public void setEbtTotal(double ebtTotal) {
        this.ebtTotal = ebtTotal;
    }

    /**
     * Get the total amount of non-EBT items in the transaction
     * @return Total amount of non-EBT items in the transaction
     */
    public double getNonEbtTotal() {
        return nonEbtTotal;
    }

    /**
     * Set the total amount of non-EBT items in the transaction
     * @param nonEbtTotal Total amount of non-EBT items in the transaction
     */
    public void setNonEbtTotal(double nonEbtTotal) {
        this.nonEbtTotal = nonEbtTotal;
    }
}
