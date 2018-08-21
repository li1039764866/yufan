/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CustomerMessageListener
 * Author:   龙
 * Date:     2018/8/21 11:17
 * Description: 消息队列监听类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package org.yufan.listener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yufan.service.ItemService;
import org.yufan.service.JedisService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 〈消息队列监听类〉
 * @author 龙
 * @create 2018/8/21 11:17
 * @since 1.0.0
 */
public class CustomerMessageListener implements MessageListener {
    private Logger LOGGER=LoggerFactory.getLogger(CustomerMessageListener.class);
    @Autowired
    private JedisService jedisService;

    @Override
    public void onMessage(Message message) {

        ActiveMQTextMessage am= (ActiveMQTextMessage) message;
        LOGGER.info("front接收到消息队列的消息{}");
        try {
            String itemId=am.getText();
            LOGGER.info("front接收到消息队列的消息{}",itemId);

            //移除缓存
            jedisService.hdel(ItemService.ITEM_CACHE,itemId);
            jedisService.hdel(ItemService.ITEM_DESC_CACHE,itemId);
            LOGGER.info("删除了{}商品的缓存------------------------",itemId);

        } catch (JMSException e) {
            e.printStackTrace();
        }


    }

}