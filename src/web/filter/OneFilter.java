package web.filter;

import web.model.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class OneFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //1.读取请求行中的URI
        String uri = request.getRequestURI();

        //2.如果本次请求与资源文件与登录或注册或论坛基础信息相关，放行
        if("/Penguin_Forum_Website_war_exploded/".equals(uri) || uri.contains("login")
                || uri.contains("/usercreate") || uri.contains("/postcomment")
                || uri.contains("/findpostsbynumberoflikes")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        //3.如果本次请求访问的是其他资源文件，需要得到在HttpSession中的user
        if(user != null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //4.拒绝请求
        request.getRequestDispatcher("/index.jsp").forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
