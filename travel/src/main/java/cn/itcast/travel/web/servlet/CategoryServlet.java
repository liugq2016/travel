package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    CategoryService categoryService = new CategoryServiceImpl();
    /**
     * 查询所有旅游线路分类信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.findAll();
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        System.out.println(request.getContextPath());
//        String s = objectMapper.writeValueAsString(categories);
//        System.out.println(s);
//        objectMapper.writeValue(response.getOutputStream(),categories);
        writeValue(categories,response);
    }


}
