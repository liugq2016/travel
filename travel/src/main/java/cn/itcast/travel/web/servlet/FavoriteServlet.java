package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
    /**
     * 通过用户真实姓名及线路id查找tab_favorite表是否存在数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findFavoriteByUidAndRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String  name = (String) request.getSession().getAttribute("name");
        String cid = request.getParameter("cid");
        System.out.println(name +":"+cid);
        boolean flag = false;
        if (name != null ){
//            用户已登录
             flag = new FavoriteServiceImpl().findOneByNameAndId(name, cid);
        }else {
//            用户未登录
        }
        writeValue(flag,response);
    }
}
