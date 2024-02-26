package com.w83ll43.alliance.apis.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.w83ll43.alliance.common.model.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "headerCheckFilter", urlPatterns = "/*")
public class HeaderCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String source = request.getHeader("X-Ca-Source");

        if (StrUtil.isNotEmpty(source) && "api-alliance-gateway".equals(source)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("{} 请求未通过网关！", request.getRemoteHost());
        response.setCharacterEncoding("UTF-8"); // 解决中文乱码
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(Result.error("非法请求！")));
    }

}
