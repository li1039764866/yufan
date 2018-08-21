package org.yufan.common;

public class ResultUtils {


    /**
     * 返回成功  不带返回结果
     */
    public static Result buildSuccess(){
        Result result=new Result();
        result.setStatus("success");
        return result;
    }


    /**
     * 返回成功  带返回结果
     */
    public static Result buildSuccess(Object obj){
        Result result=new Result();
        result.setStatus("success");
        result.setData(obj);
        return result;
    }


    /**
     * 返回失败
     */
    public static Result buildFail(Integer code,String message){
        Result result=new Result();
        result.setStatus("fail");
        result.setCode(code);
        result.setMessage(message);
        return result;
    }


}
