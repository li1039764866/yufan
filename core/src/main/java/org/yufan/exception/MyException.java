package org.yufan.exception;
//异常处理的实体类
public class MyException extends  Exception{

    private String message;

    public MyException(String message){
        super();
        this.message=message;
    }
    public MyException(){
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
