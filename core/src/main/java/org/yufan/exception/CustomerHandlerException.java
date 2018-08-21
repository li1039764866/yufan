package org.yufan.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.yufan.common.ResponseUtils;
import org.yufan.common.Result;
import org.yufan.common.ResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 编写全局的异常处理器,实现HandlerExceptionResolver类并在springmvc.xml中配置
 */
public class CustomerHandlerException implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {


        //1.判断异常类型   预期异常还是不可预期
        String msg;
        if(exception instanceof MyException){
            //如果是自定义异常，直接取出异常信息
            msg=exception.getMessage();
        }else{
            //不可预期的异常
            exception.printStackTrace();
            msg="对不起，系统开小差了!";
        }

        //判断处理器上面有没有ResponseBody注解或者处理器返回的类型ResponseEntity

        //获取处理器的方法
        HandlerMethod handlerMethod= (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //查找上面是否有这个注解
        ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);
        ModelAndView modelAndView=new ModelAndView();
        if(responseBody!=null || ResponseEntity.class.getName().equals(method.getReturnType().getName())){
            //返回json数据
            Result result=ResultUtils.buildFail(500,msg);
            ResponseUtils.responseToObject(response,result);
            return modelAndView;
        }

        //返回error.jsp视图
        modelAndView.addObject("msg",msg);
        modelAndView.setViewName("error");
        return modelAndView;
    }


}
