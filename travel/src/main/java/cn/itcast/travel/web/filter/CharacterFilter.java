package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@WebFilter( "/*")
public class CharacterFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)req;
        HttpServletResponse httpServletResponse = (HttpServletResponse)resp;
        if (httpServletRequest.getMethod().equalsIgnoreCase("post")){
            httpServletRequest.setCharacterEncoding("utf-8");
        }
//        resp.setContentType("text/html;utf-8");
        httpServletResponse.setContentType("text/tml;utf-8");
        chain.doFilter(httpServletRequest,httpServletResponse);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
