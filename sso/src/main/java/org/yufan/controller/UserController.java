package org.yufan.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yufan.bean.User;
import org.yufan.common.Result;
import org.yufan.common.ResultUtils;
import org.yufan.exception.MyException;
import org.yufan.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/sso")
@Controller
public class UserController {





    @Autowired
    private UserService userService;

    private Logger LOGGER=LoggerFactory.getLogger( UserController.class);


    /**
     *
     * @param user
     * @param bindingResult
     * @return
     * @throws MyException
     */
    @RequestMapping(value = "/register",method =RequestMethod.GET)
    @ResponseBody
    public Result register(@Valid  User user, BindingResult bindingResult) throws MyException {
        //判断是否有错误
        if(bindingResult.hasErrors()){
            //校验失败
            //获取校验信息
            List<ObjectError> errors = bindingResult.getAllErrors();
            List<String> list= new ArrayList<>();
            for (ObjectError error:errors) {
                //获取每一个错误信息
                String msg=error.getDefaultMessage();
                list.add(msg);
            }
            //拼接字符串
            String message=org.apache.commons.lang3.StringUtils.join(list,",");
            LOGGER.info("校验失败，信息为{}",message);
            return ResultUtils.buildFail(105,message);
        }
        userService.register(user);
        return ResultUtils.buildSuccess();
    }


    /**
     *
     * @param username
     * @param password
     * @return
     * @throws MyException
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public MappingJacksonValue login(String username,String password,String callback) throws MyException {
        if(StringUtils.isEmpty(username)){
            LOGGER.info("用户名为空!");
            return new MappingJacksonValue(ResultUtils.buildFail(111,"用户名为空!"));
        }
        if(StringUtils.isEmpty(password)){
            LOGGER.info("密码为空!");
            return new MappingJacksonValue(ResultUtils.buildFail(111,"密码为空!"));

        }
        String token=userService.login(username,password);
        MappingJacksonValue mappingJacksonValue;
        if(StringUtils.isEmpty(token)){
            LOGGER.info("用户{},登录失败",username);
            mappingJacksonValue=  new MappingJacksonValue(ResultUtils.buildFail(111,"登录失败!"));
        }else {
            LOGGER.info("登录成功，当前登录的用户为{}",username);
            mappingJacksonValue= new MappingJacksonValue(ResultUtils.buildSuccess(token));
        }

        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

    /**
     *
     * @param token
     * @return String 根据令牌判断用户是否登录，重定向到首页或者登陆页面
     * @throws MyException
     */
    @RequestMapping(value = "/query/{token}",method = RequestMethod.GET)
    public String queryUserByToken(@PathVariable("token")  String token) throws MyException, IOException {
        LOGGER.info("当前查询用户信息的token为{}",token);
        User user=userService.queryUserByToken(token);
        System.out.println("new ModelAndView");
        if(user==null){
            return "redirect:http://www.yufan.com/user/login.html";
        }
        return "redirect:http://www.yufan.com";
    }


    /**
     *
     * @param username
     * @return
     * @throws MyException
     */
    @RequestMapping(value = "/check/{username}/1",method = RequestMethod.GET)
    @ResponseBody
    public MappingJacksonValue checkUsername(@PathVariable("username")String username,String callback ) throws MyException {
        LOGGER.info("当前查询用户信息的token为{}",username);
        User user=new User();
        user.setUsername(username);
        user=userService.queryUserBySelective(user);
        MappingJacksonValue mappingJacksonValue;
        if(user!=null) {
            mappingJacksonValue = new MappingJacksonValue(ResultUtils.buildFail(111, "用户名已经存在"));
        }else {
            mappingJacksonValue = new MappingJacksonValue(ResultUtils.buildSuccess());
        }
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }



}
