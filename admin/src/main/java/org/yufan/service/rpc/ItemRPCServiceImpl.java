/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ItemRPCServiceImpl
 * Author:   龙
 * Date:     2018/8/19 15:01
 * Description: 商品的远程调用接口实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package org.yufan.service.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yufan.bean.Item;
import org.yufan.bean.ItemDesc;
import org.yufan.service.ItemDescService;
import org.yufan.service.ItemService;

/**
 * 〈商品的远程调用接口实现〉
 * @author 龙
 * @create 2018/8/19 15:01
 * @since 1.0.0
 */
@Service("itemRPCService")
public class ItemRPCServiceImpl implements ItemRPCService{

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @Override
    public Item queryItemById(Long itemId) {
        return itemService.queryById(itemId);
    }

    @Override
    public ItemDesc queryItemDescById(Long itemId) {
        return itemDescService.queryById(itemId);
    }
}