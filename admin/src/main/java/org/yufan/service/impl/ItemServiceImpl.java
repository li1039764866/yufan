package org.yufan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.yufan.bean.Item;
import org.yufan.bean.ItemDesc;
import org.yufan.common.ItemStatus;
import org.yufan.service.ItemDescService;
import org.yufan.service.ItemService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void addItem(Item item, String desc) {

        //保存商品
        item.setStatus(2);//设置商品的状态为下架
        item.setUpdated(new Date());
        item.setCreated(item.getUpdated());
        this.save(item);

        //保存商品详情
        ItemDesc itemDesc=new ItemDesc();
        //设置商品id
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(item.getCreated());
        itemDesc.setCreated(item.getUpdated());
        itemDescService.save(itemDesc);

    }

    @Override
    public void updateItem(Item item, String desc) {

        item.setUpdated(new Date());

        ItemDesc itemDesc = itemDescService.queryById(item.getId());
        itemDesc.setUpdated(item.getUpdated());
        itemDesc.setItemDesc(desc);
        //先删除缓存再持久化数据
        //通知前台系统删除缓存，维持缓存一致性
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(String.valueOf(item.getId()));
            }
        });
        this.update(item);
        itemDescService.update(itemDesc);
    }

    @Override
    public void setItemStatus(Long[] ids, ItemStatus itemStatus) {
        Item item;
        for (int i = 0; i <ids.length ; i++) {
            item=this.queryById(ids[i]);
            item.setStatus(ItemStatus.getValue(itemStatus));
            this.update(item);
        }
    }
}
