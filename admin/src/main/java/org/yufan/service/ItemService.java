package org.yufan.service;

import org.yufan.bean.Item;
import org.yufan.common.ItemStatus;

public interface ItemService extends BaseService<Item>{

    public void addItem(Item item,String desc);

    public void updateItem(Item item, String desc);

    public void setItemStatus(Long[] ids, ItemStatus itemStatus);
}
