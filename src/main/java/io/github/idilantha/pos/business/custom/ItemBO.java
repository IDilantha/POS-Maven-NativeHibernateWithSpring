package io.github.idilantha.pos.business.custom;

import io.github.idilantha.pos.business.SuperBO;
import io.github.idilantha.pos.dto.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO {

    void saveItem(ItemDTO itemDTO) throws Exception;

    void updateItem(ItemDTO item) throws Exception;

    void deleteItem(String itemCode) throws Exception;

    List<ItemDTO> findAllItems() throws Exception;

    String getLastItemCode() throws Exception;

    ItemDTO findItem(String itemCode) throws Exception;

    List<String> getAllItemCodes() throws Exception;

}
