package org.yufan.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T> {

    public T queryById(Long id);

    public List<T> queryAllList();

    public T queryOneByCondition(T t);


    public List<T> queryListByCondition(T t);


    public PageInfo<T> queryPageListByCondition(Integer starIndex,Integer pageSize,T t);

    public void save(T t);

    public void update(T  t);

    public void delete(Long id);

    public void deleteByConditions(Class<T> clazz,String property,List<Object> values);

}
