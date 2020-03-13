package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    protected ObjectMapper objectMapper = null;
    /**
     * 任务的分发
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取访问路径
        String requestURI = req.getRequestURI();
//        获取执行的方法名
        int index = requestURI.lastIndexOf("/")+1;
        String method = requestURI.substring(index);
//        得到方法并执行
        try {
            Method methodname = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            methodname.invoke(this,req,resp);
//            System.out.println("BaseSevlet被执行了");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将object序列化成json并写回客户端
     * @param obj
     * @param response
     */
    protected void writeValue(Object obj,HttpServletResponse response) throws IOException {
        objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将object序列化成json并返回序列化后的值
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
  protected  String wirteValueAsString(Object obj) throws JsonProcessingException {
        objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(obj);
      return json;
  }
}
