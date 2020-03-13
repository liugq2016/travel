package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet( "/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        设置获取数据的编码格式
        request.setCharacterEncoding("utf-8");
//        获取数据
        String cid = request.getParameter("cid");
        String currentpage = request.getParameter("currentpage");
        String pagesize = request.getParameter("pagesize");
        String rname = request.getParameter("rname");
//       调用route的service方法
        PageBean<Route> pageBean = routeService.pageQuery(cid,currentpage,pagesize,rname);
        writeValue(pageBean,response);

    }

    /**
     * 通过rid查询某一旅游路线的的商品、图片、卖家信息并封装在Route
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void productDetailsPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String rid = req.getParameter("rid");
        Route route = routeService.productDetailsPage(rid);
        if (route == null){
            writeValue("未查找到数据",resp);
        }else {
            writeValue(route,resp);
        }

    }
}
