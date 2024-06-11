package repository;

import model.Item;

import java.util.List;

public interface ItemRepository {
    void save(Item item);
    void delete(String barcode);
    void update(Item item);
    Item findByBarcode(String barcode);
    List<Item> findAll();
}
