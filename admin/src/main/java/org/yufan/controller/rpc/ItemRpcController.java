package org.yufan.controller.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yufan.bean.Item;
import org.yufan.bean.ItemDesc;
import org.yufan.common.Result;
import org.yufan.common.ResultUtils;
import org.yufan.service.ItemDescService;
import org.yufan.service.ItemService;

@Controller
@RequestMapping("/rpc/item")
public class ItemRpcController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public Result  queryItem(@PathVariable("itemId")Long itemId ){
        Item item = itemService.queryById(itemId);
        if(item==null){
            return ResultUtils.buildFail(101,"查询的商品基本信息不存在，id:"+itemId);
        }
        return ResultUtils.buildSuccess(item);
    }


    @RequestMapping(value = "/desc/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public Result  queryItemDesc(@PathVariable("itemId")Long itemId ){
        ItemDesc itemDesc = itemDescService.queryById(itemId);
        if(itemDesc==null){
            return ResultUtils.buildFail(102,"查询的商品详情不存在,id:"+itemId);
        }
        return ResultUtils.buildSuccess(itemDesc);
    }

}
