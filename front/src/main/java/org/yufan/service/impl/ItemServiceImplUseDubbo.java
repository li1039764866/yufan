/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ItemServiceImplUseDubbo
 * Author:   龙
 * Date:     2018/8/19 16:32
 * Description: 使用Dubbo实现的远程接口的调用
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package org.yufan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yufan.bean.Item;
import org.yufan.bean.ItemDesc;
import org.yufan.common.JSONUtils;
import org.yufan.exception.MyException;
import org.yufan.service.ItemService;
import org.yufan.service.JedisService;
import org.yufan.service.rpc.ItemRPCService;

import java.io.IOException;

/**
 * 〈使用Dubbo实现的远程接口的调用〉
 * @author 龙
 * @create 2018/8/19 16:32
 * @since 1.0.0
 */

@Service
@Qualifier("itemServiceImplUseDubbo")
public class ItemServiceImplUseDubbo implements ItemService {

    @Autowired //不能取消这个注释，在运行的时候，Dubbo会连接到这个服务并通过Soring将Bean注入
    private ItemRPCService itemRPCService;

    @Autowired
    private JedisService jedisService;

    private Logger LOGGER=LoggerFactory.getLogger(ItemServiceImplUseDubbo.class);

    @Override
    public Item queryItemById(Long itemId) throws MyException, IOException {
        String json=jedisService.hget(ITEM_CACHE,String.valueOf(itemId));
        if(StringUtils.isEmpty(json)){
            Item item=itemRPCService.queryItemById(itemId);
            if(item==null){
                LOGGER.error("后台調用服務異常{}",itemId);
                throw new MyException("後台調用服務異常{}");
            }
            jedisService.hset(ITEM_CACHE,String.valueOf(itemId),JSONUtils.objectToJson(item));
            return item;
        }
        System.out.println(json);
        return JSONUtils.jsonToPojo(json,Item.class);
    }

    @Override
    public ItemDesc queryItemDescById(Long itemId) throws MyException, IOException {
        String json=jedisService.hget(ITEM_DESC_CACHE,String.valueOf(itemId));
        if(StringUtils.isEmpty(json)){
            ItemDesc itemDesc=itemRPCService.queryItemDescById(itemId);
            if(itemDesc==null){
                LOGGER.error("后台調用服務異常{}",itemId);
                throw new MyException("後台調用服務異常{}");
            }
            jedisService.hset(ITEM_DESC_CACHE,String.valueOf(itemId),
                    JSONUtils.objectToJson(itemDesc));
            return itemDesc;
        }
        return JSONUtils.jsonToPojo(json,ItemDesc.class);
    }
}