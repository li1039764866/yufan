package org.yufan.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yufan.bean.Item;
import org.yufan.bean.ItemDesc;
import org.yufan.common.HttpClientUtil;
import org.yufan.common.JSONUtils;
import org.yufan.exception.MyException;
import org.yufan.service.ItemService;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {
    //访问下面的的网址需要开启nginx的代理，不然就需要访问http://www.yufan.com:8080/rest/rpc/item/
    //前台需要访问http://localhost:8081/item/40.html
    public static final String ITEM_URL="http://www.yufan.com/rest/rpc/item/";
    public static final String ITEM_DESC_URL="http://www.yufan.com/rest/rpc/item/desc/";

    @Override
    public Item queryItemById(Long itemId) throws MyException, IOException {
        //调用后台系统的接口返回数据
        String json = HttpClientUtil.doGet(ITEM_URL + itemId);
        if(StringUtils.isEmpty(json)){
            throw  new MyException("查询的商品不存在!");
        }
        ObjectMapper objectMapper=new ObjectMapper();
        //获取返回数据的根节点
        JsonNode jsonNode = objectMapper.readTree(json);
        //获取根节点下的data节点
        JsonNode data = jsonNode.get("data");
        //利用JsonNode类的toString()直接将data转化为Json数据
        String item_data=data.toString();
        //利用工具类将Json数据转化为实体类对象
        return JSONUtils.jsonToPojo(item_data,Item.class);
    }


    @Override
    public ItemDesc queryItemDescById(Long itemId) throws MyException, IOException {
        //调用后台系统的接口返回数据
        String json = HttpClientUtil.doGet(ITEM_DESC_URL + itemId);
        if(StringUtils.isEmpty(json)){
            throw  new MyException("查询的基本信息商品不存在!");
        }
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode data = jsonNode.get("data");
        String item_desc_data=data.toString();
        return JSONUtils.jsonToPojo(item_desc_data,ItemDesc.class);
    }
}
