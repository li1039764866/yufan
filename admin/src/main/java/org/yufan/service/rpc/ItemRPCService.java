/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ItemRPCService
 * Author:   龙
 * Date:     2018/8/19 14:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package org.yufan.service.rpc;

import org.yufan.bean.Item;
import org.yufan.bean.ItemDesc;

/**
 * 〈商品的远程调用接口〉
 *
 * @author 龙
 * @create 2018/8/19 15:00
 * @since 1.0.0
 */
public interface ItemRPCService {
    public Item queryItemById(Long itemId);

    public ItemDesc queryItemDescById(Long itemId);

}