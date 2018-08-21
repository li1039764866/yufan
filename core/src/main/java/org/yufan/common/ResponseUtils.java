package org.yufan.common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {

    public static void responseToJson(HttpServletResponse response, String json) {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void responseToObject(HttpServletResponse response, Object obj) {
        String json=JSONUtils.objectToJson(obj); //利用工具类将对象转换为JSON
        response.setContentType("application/json;charset=UTF-8");//设置响应格式为json
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(json);//将json绑定给response返回去
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
