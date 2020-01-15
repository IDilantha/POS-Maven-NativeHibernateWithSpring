package io.github.idilantha.pos.dao.custom;

import io.github.idilantha.pos.dao.CrudDAO;
import io.github.idilantha.pos.entity.Item;

public interface ItemDAO extends CrudDAO<Item, String> {

    String getLastItemCode() throws Exception;
}
