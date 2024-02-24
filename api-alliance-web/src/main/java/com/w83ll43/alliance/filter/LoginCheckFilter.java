package com.w83ll43.alliance.filter;

import com.alibaba.fastjson2.JSON;
import com.w83ll43.alliance.common.model.Result;
import com.w83ll43.alliance.common.model.entity.User;
import com.w83ll43.alliance.common.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    /**
     * 路径匹配器
     */
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1、获取本次请求的 URI
        String uri = request.getRequestURI();

        // 定义不需要处理的请求路径
        String[] urls = new String[]{
                "/user/login",
                "/user/register"
        };

        // 2、判断本次请求是否需要处理
        boolean check = check(urls, uri);

        // 3、如果不需要处理则直接放行
        if (check) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4、判断登录状态 如果已经登录则直接放
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            // 设置线程副本的局部变量
            BaseContext.setCurrentId(user.getId());
            filterChain.doFilter(request, response);
            return;
        }

        // 5、如果未登录则返回未登录结果 如果未登录则返回未登录结果 通过输出流方式向客户端响应数据
        response.setCharacterEncoding("UTF-8"); // 解决中文乱码
        response.getWriter().write(JSON.toJSONString(Result.error("用户未登录")));
    }

    /**
     * 匹配路径
     * @param urls 待匹配路径数组
     * @param uri  路径
     * @return 是否匹配
     */
    private boolean check(String[] urls, String uri) {
        for (String url : urls) {
            if (PATH_MATCHER.match(url, uri)) {
                return true;
            }
        }
        return false;
    }
}
