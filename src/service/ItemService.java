package service;

import model.Item;
import repository.ItemRepository;

import java.util.List;

public class ItemService {
    /** The item repository. */
    private ItemRepository itemRepository;

    /**
     * Instantiates a new item service.
     * @param itemRepository the item repository
     */
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Adds the item via the item repository.
     * @param item the item
     */
    public void addItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * Removes the item via the item repository.
     * @param barcode the barcode
     */
    public void removeItem(String barcode) {
        itemRepository.delete(barcode);
    }

    /**
     * Updates the item price via the item repository.
     * @param barcode the barcode
     * @param newPrice the new price
     */
    public void UpdateItemPrice(String barcode, double newPrice) {
        Item item = itemRepository.findByBarcode(barcode);

        if (item != null) {
            item.setPrice(newPrice);
            itemRepository.update(item);
        }
    }

    /**
     * Gets the item by barcode via the item repository.
     * @param barcode the barcode
     * @return the item by barcode
     */
    public Item getItemByBarcode(String barcode) {
        return itemRepository.findByBarcode(barcode);
    }

    /**
     * Gets all items via the item repository.
     * @return the all items
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
