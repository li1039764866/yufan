package org.yufan.service;

import org.yufan.bean.User;
import org.yufan.exception.MyException;

public interface UserService {
    /**
     * 用户注册
     * @param user
     */
    public void register(User user) throws MyException;

    /**
     *
     * @param username
     * @param pssword
     * @return token 令牌
     */
    public String login(String username,String pssword) throws MyException;

    /**
     * 根据Token查询用户信息
     * @param token
     * @return
     */
    public User queryUserByToken(String token) throws MyException;

    public User queryUserBySelective(User user);
}
