package org.yufan.service;

import org.yufan.bean.Item;
import org.yufan.bean.ItemDesc;
import org.yufan.exception.MyException;

import java.io.IOException;

public interface ItemService {

    public static final String ITEM_CACHE="item_cache";
    public static final String ITEM_DESC_CACHE="item_desc_cache";

    public Item   queryItemById(Long itemId) throws MyException, IOException;

    public ItemDesc queryItemDescById(Long itemId) throws MyException, IOException;

}
