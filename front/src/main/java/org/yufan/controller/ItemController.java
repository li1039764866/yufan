package org.yufan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yufan.bean.Item;
import org.yufan.bean.ItemDesc;
import org.yufan.exception.MyException;
import org.yufan.service.ItemService;

import java.io.IOException;

@RequestMapping("/item")
@Controller
public class ItemController {

    @Autowired
    @Qualifier("itemServiceImplUseDubbo")
    private ItemService itemService;


    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public String toItem(Model model, @PathVariable("itemId")Long itemId) throws IOException, MyException {
        System.out.println("查询到的商品ID为："+itemId);
        //int x=1/0;此异常没有被处理，为不可预期的异常
        //此方法会调用后台的rpc接口，故访问该页面需要保持后台项目的运行
        Item item = itemService.queryItemById(itemId);
        if(item==null||item.getImage()==null||item.getImage().length()==0){
            throw new MyException("商品不存在，请联系管理员!");
        }
        //如果当前商品不存在，会触发空指针异常，进而调用全局异常处理器
        String[] images = item.getImage().split(",");

        if(images==null||images.length==0){
            throw new MyException("商品的图片不存在，请联系管理员!");
        }
        ItemDesc itemDesc = itemService.queryItemDescById(itemId);
        model.addAttribute("images",images);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);

        return "item";
    }




}
