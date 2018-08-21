package org.yufan.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//自定义SpringMVC的日期转换器替换默认日期转换器
public class DateConverter implements Converter<String,Date> {

    @Override
    public Date convert(String source) {

        //将前台传递的字符串转化为需要的Date类型
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
