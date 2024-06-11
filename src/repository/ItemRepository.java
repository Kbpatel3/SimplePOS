package repository;

import model.Item;

import java.util.List;

public interface ItemRepository {
    /**
     * Save item to database
     * @param item item to save
     */
    void save(Item item);

    /**
     * Delete item from database
     * @param barcode barcode of item to delete
     */
    void delete(String barcode);

    /**
     * Update item in database
     * @param item item to update
     */
    void update(Item item);

    /**
     * Find item by barcode
     * @param barcode barcode of item to find
     * @return item with the barcode
     */
    Item findByBarcode(String barcode);

    /**
     * Find all items in database
     * @return list of all items
     */
    List<Item> findAll();
}
