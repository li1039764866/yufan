package org.yufan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.yufan.service.BaseService;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

    @Override
    public T queryById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> queryAllList() {
        return mapper.selectAll();
    }

    @Override
    public T queryOneByCondition(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public List<T> queryListByCondition(T t) {
        return mapper.select(t);
    }

    @Override
    public PageInfo<T> queryPageListByCondition(Integer starIndex, Integer pageSize, T t) {
        if(starIndex<0||pageSize<0){
            return null;
        }
        PageHelper.startPage(starIndex,pageSize);
        List<T> list = mapper.select(t);
        return new PageInfo<T>(list);
    }

    @Override
    public void save(T t) {
        mapper.insertSelective(t);
    }

    @Override
    public void update(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void delete(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByConditions(Class<T> clazz, String property, List<Object> values) {

        Example example=new Example(clazz);
        example.createCriteria().andIn(property,values);
        mapper.deleteByExample(example);
    }
}