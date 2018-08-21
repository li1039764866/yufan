package org.yufan.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yufan.bean.Item;
import org.yufan.bean.ItemDesc;
import org.yufan.common.ItemStatus;
import org.yufan.common.Result;
import org.yufan.common.ResultUtils;
import org.yufan.result.EasyUIResult;
import org.yufan.service.ItemDescService;
import org.yufan.service.ItemService;

@RequestMapping("/item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result itemAdd(Item item, String desc){
        //int x=1/0;
        itemService.addItem(item,desc);
        return ResultUtils.buildSuccess();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Result  itemUpdate(Item item,String desc){
        itemService.updateItem(item,desc);
        return ResultUtils.buildSuccess();
    }
    @RequestMapping(value = "/instock",method = RequestMethod.POST)
    @ResponseBody
    public Result  itemInstock(Long[] ids){
        itemService.setItemStatus(ids,ItemStatus.INSTOCK);
        return ResultUtils.buildSuccess();
    }
    @RequestMapping(value = "/reshelf",method = RequestMethod.POST)
    @ResponseBody
    public Result  itemReshelf(Long[] ids){
        itemService.setItemStatus(ids,ItemStatus.NORMAL);
        return ResultUtils.buildSuccess();
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result  itemDelete(Long[] ids){
        itemService.setItemStatus(ids,ItemStatus.DELETE);
        Result<Integer> result=new Result<>();
        result.setStatus("200");
        return result;
    }



    @RequestMapping(value = "/list")
    @ResponseBody
    public EasyUIResult list(@RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "rows",defaultValue = "5")Integer rows){

        PageInfo<Item> pageInfo = itemService.queryPageListByCondition(page, rows, null);

        EasyUIResult easyUIResult=new EasyUIResult((int)pageInfo.getTotal(),pageInfo.getList());
        return easyUIResult;
    }

    @RequestMapping(value = "/desc/{itemId}")
    @ResponseBody
    public ItemDesc   queryItemDesc(@PathVariable Long itemId){

       
        return itemDescService.queryById(itemId);

    }


}
