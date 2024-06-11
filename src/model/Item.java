package model;

public class Item {
    /** The name of the item */
    private String name;

    /** The barcode of the item */
    private String barcode;

    /** The price of the item */
    private double price;

    /** The tax rate of the item */
    private double taxRate;

    /** Whether the item is EBT eligible */
    private boolean isEbtEligible;

    /**
     * Constructor for the Item class
     * @param name The name of the item
     * @param barcode The barcode of the item
     * @param price The price of the item
     * @param taxRate The tax rate of the item
     * @param isEbtEligible Whether the item is EBT eligible
     */
    public Item(String name, String barcode, double price, double taxRate, boolean isEbtEligible) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.taxRate = taxRate;
        this.isEbtEligible = isEbtEligible;
    }

    /**
     * Constructor for the Item class with no parameters
     * Used for creating an empty item
     */
    public Item() {
        this.name = "";
        this.barcode = "";
        this.price = 0;
        this.taxRate = 0;
        this.isEbtEligible = false;
    }

    /**
     * Getter for the name of the item
     * @return The name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the item
     * @param name The name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the barcode of the item
     * @return The barcode of the item
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Setter for the barcode of the item
     * @param barcode The barcode of the item
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Getter for the price of the item
     * @return The price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for the price of the item
     * @param price The price of the item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for the tax rate of the item
     * @return The tax rate of the item
     */
    public double getTaxRate() {
        return taxRate;
    }

    /**
     * Setter for the tax rate of the item
     * @param taxRate The tax rate of the item
     */
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Getter for whether the item is EBT eligible
     * @return Whether the item is EBT eligible
     */
    public boolean isEbtEligible() {
        return isEbtEligible;
    }

    /**
     * Setter for whether the item is EBT eligible
     * @param ebtEligible Whether the item is EBT eligible
     */
    public void setEbtEligible(boolean ebtEligible) {
        isEbtEligible = ebtEligible;
    }
}
