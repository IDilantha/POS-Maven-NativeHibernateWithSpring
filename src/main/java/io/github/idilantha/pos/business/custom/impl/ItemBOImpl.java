package io.github.idilantha.pos.business.custom.impl;

import io.github.idilantha.pos.business.custom.ItemBO;
import io.github.idilantha.pos.dao.custom.ItemDAO;
import io.github.idilantha.pos.dao.custom.OrderDetailDAO;
import io.github.idilantha.pos.dto.ItemDTO;
import io.github.idilantha.pos.entity.Item;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class ItemBOImpl implements ItemBO {

    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;

    @Override
    public void saveItem(ItemDTO item) throws Exception {
        itemDAO.save(new Item(item.getCode(),
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));

    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {
        itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
    }

    @Override
    public void deleteItem(String itemCode) throws Exception {
        if (orderDetailDAO.existsByItemCode(itemCode)) {
            new Alert(Alert.AlertType.ERROR, "Item Already exist in an order , Unable to Delete").show();
            return;
        }else {
            itemDAO.delete(itemCode);
        }
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        List<Item> allItems = itemDAO.findAll();
        List<ItemDTO> dtos = new ArrayList<>();
        for (Item item : allItems) {
            dtos.add(new ItemDTO(item.getCode(),
                    item.getDescription(),
                    item.getQtyOnHand(),
                    item.getUnitPrice()));
        }
        return dtos;
    }

    @Override
    public String getLastItemCode() throws Exception {
        String lastItemCode = itemDAO.getLastItemCode();
        return lastItemCode;

    }

    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        Item item = itemDAO.find(itemCode);
        return new ItemDTO(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice());
    }

    @Override
    public List<String> getAllItemCodes() throws Exception {
        List<Item> allItems = itemDAO.findAll();
        List<String> codes = new ArrayList<>();
        for (Item item : allItems) {
            codes.add(item.getCode());
        }
        return codes;
    }
}
