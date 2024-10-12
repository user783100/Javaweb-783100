package pers.wzt.web.fliter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    private List<String> exPaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化排除列表
        exPaths = new ArrayList<>();
        exPaths.add("/login.html");
      exPaths.add("/loginServlet");
        exPaths.add("/welcome.html");
        exPaths.add("/indexServlet");
        exPaths.add("/hello.jsp");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求路径
        String requestPath = request.getServletPath();

        // 如果请求路径在排除列表中，允许请求通过
        if (isExcluded(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session!= null && session.getAttribute("user")!= null) {
            // 用户已登录，允许请求继续
            filterChain.doFilter(request, response);
        } else {
            // 用户未登录，重定向到登录页面
            response.sendRedirect("/login.html");
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isExcluded(String path) {
        // 判断当前请求路径是否在排除列表中
        for (String exPath : exPaths) {
            if (path.startsWith(exPath)) {
                return true;
            }
        }
        return false;
    }
}
