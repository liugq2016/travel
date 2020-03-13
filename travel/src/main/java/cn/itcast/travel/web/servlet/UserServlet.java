package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    /**
     * 用户注册方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //        返回错误信息的封装类
        ResultInfo info = new ResultInfo();
//        获取前端输入的验证码
        String check = request.getParameter("check");
//        获取session域中的生成的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        if (checkcode_server.equalsIgnoreCase(check) && check != null){
            Map<String, String[]> parameterMap = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user,parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            UserService userService = new UserServiceImpl();
            Boolean flag = userService.regist(user);
            if (flag){
                //注册成功
                info.setFlag(true);
            }else {
                //注册失败
                info.setFlag(false);
                info.setErrorMsg("注册失败!");
            }
//            //将info序列化成json数据
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(info);
//            //将json数据写回客户端
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().write(json);
        }else {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        }
        //将info序列化成json数据
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(info);
        //将json数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    /**
     * 用户登录方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
//        获取用户信息
        Map<String, String[]> map = request.getParameterMap();
//        获取输入的验证码
        String check = request.getParameter("check");
//        获取生成的验证码
        String checkcode = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String msg = null;//设置错误信息
        ResultInfo info = new ResultInfo();//信息封装类
        if (checkcode.equalsIgnoreCase(check) && check != null){
            UserService userService = new UserServiceImpl();
            User user1 = userService.login(user);
            if (user1 == null){
//            查询结果为空
                msg= "账号或密码错误！";
                info.setErrorMsg(msg);
                info.setFlag(false);
            }else {
//            查询结果不为空
                if ("N".equalsIgnoreCase(user1.getStatus())){
                    msg = "请先登陆邮箱激活!";
                    info.setErrorMsg(msg);
                    info.setFlag(false);
                    request.getSession().setAttribute("name",user1.getName());
                }else {
                    info.setFlag(true);
                    request.getSession().setAttribute("name",user1.getName());
                }
            }
        }else {
            msg = "验证码错误";
            info.setFlag(false);
            info.setErrorMsg(msg);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }

    /**
     * 用户退出方法
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */

    public void exits(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getSession().invalidate();
        res.sendRedirect(req.getContextPath()+"/login.html");
    }

    /**
     * 已登录用户信息
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    public void logininfomation(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String name = (String) req.getSession().getAttribute("name");
        ObjectMapper objectMapper = new ObjectMapper();
        ResultInfo info = new ResultInfo();
        if (name != null){
            info.setFlag(true);
            info.setData(name);
        }else {
            info.setFlag(false);
        }
        res.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(res.getOutputStream(),info);
    }

    /**
     * 获取验证码
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */

    public void checkcode(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //服务器通知浏览器不要缓存
        res.setHeader("pragma","no-cache");
        res.setHeader("cache-control","no-cache");
        res.setHeader("expires","0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        req.getSession().setAttribute("CHECKCODE_SERVER",checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image,"PNG",res.getOutputStream());
    }
    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 用户激活
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String code = req.getParameter("code");
        String msg = null;
        if (code!=null) {
            //有激活码
            UserService userService = new UserServiceImpl();
            boolean flag = userService.active(code);
            if (flag) {
                //激活成功
                msg = "激活成功,请<a href = '../login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败,请联系管理员";
            }
        }else {
            msg = "激活失败,请先<a href = '../register.html'>注册</a>";
        }
        res.setContentType("text/html;charset=utf-8");
        res.getWriter().write(msg);
    }
}
