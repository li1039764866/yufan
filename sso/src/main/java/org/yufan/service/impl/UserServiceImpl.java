package org.yufan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.yufan.bean.User;
import org.yufan.common.JSONUtils;
import org.yufan.exception.MyException;
import org.yufan.mapper.UserMapper;
import org.yufan.service.JedisService;
import org.yufan.service.UserService;

import java.util.Date;
import java.util.UUID;

/**
* @file UserServiceImpl.java
* @author lizi
* @email 1039764866@qq.com
* @date 2018/8/17 15:30
*/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisService jedisService;
    private static final String USER_LOGIN="gfsdgvafvqweac:";
    private Logger LOGGER=LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public void register(User user) throws MyException {
        if(user==null){
            LOGGER.info("用户信息为空");
            return;
        }
        //1.用户名 电话 邮箱不能重复
        if(queryByPhone(user.getPhone())!=null){
            //电话号码已经注册
            LOGGER.info("电话号码已经注册了{}",user.getPhone());
            throw  new MyException("电话号码已经注册了!");
        }
        if(queryByEmail(user.getEmail())!=null){
            //电话号码已经注册
            LOGGER.info("邮箱已经注册了{}",user.getEmail());
            throw  new MyException("邮箱已经注册了!");
        }
        if(queryByUsername(user.getUsername())!=null){
            //电话号码已经注册
            LOGGER.info("用户名已经注册了{}",user.getUsername());
            throw  new MyException("用户名已经注册了!");
        }

        //2.将用户密码进行MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());

        //3.添加用户到数据库中
        userMapper.insertSelective(user);
    }
    public User queryByPhone(String phone) {
        User record=new User();
        record.setPhone(phone);
        return userMapper.selectOne(record);
    }

    private User queryByUsername(String username) {
        User record=new User();
        record.setUsername(username);
        return userMapper.selectOne(record);
    }

    private User queryByEmail(String email) {
        User record=new User();
        record.setEmail(email);
        return userMapper.selectOne(record);
    }

    @Override
    public String login(String username, String password) throws MyException {
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            LOGGER.info("参数为空!");
            throw  new MyException("参数为空!");
        }
        //密码加密
        User record=new User();
        record.setUsername(username);
        System.out.println("password:"+password);
        record.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        System.out.println("user:"+record.getUsername());
        System.out.println("passwordMD5:"+record.getPassword());
        User user = userMapper.selectOne(record);
        if(user==null){ //数据库无当前用户
            LOGGER.info("用户名密码错误:{}",username);
            return null;
            //throw  new MyException("用户名密码错误!");
        }
        //存储用户数据都redis中,返回登录令牌
        return getToken(user);

    }

    public String getToken(User user) {
        //处理登录
        String token=getToken();
        //设置登录信息到redis服务器
        jedisService.set(USER_LOGIN+token,JSONUtils.objectToJson(user));
        //设置失效时间
        jedisService.expire(USER_LOGIN+token,60*60*2);
        return token;
    }
    /**
     * 生成令牌
     * @return 随机令牌
     */
    public String getToken(){
        String uuid= UUID.randomUUID().toString();
        return DigestUtils.md5DigestAsHex(uuid.getBytes());
    }

    @Override
    public User queryUserByToken(String token) throws MyException {
        if(StringUtils.isEmpty(token)){
            throw  new MyException("token不能为空");
        }
        LOGGER.info("当前查询用户信息的token为{}",token);
        String json=jedisService.get(USER_LOGIN+token);
        if(StringUtils.isEmpty(json)){
            LOGGER.info("登录失效!,token为{}",token);
            return null;
        }
        //刷新失效时间，更新为两小时
        jedisService.expire(USER_LOGIN+token,60*60*2);
        return JSONUtils.jsonToPojo(json,User.class);
    }

    @Override
    public User queryUserBySelective(User user) {
        return userMapper.selectOne(user);
    }
}
