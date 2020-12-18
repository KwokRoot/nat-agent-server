package org.devops.filter;

import org.devops.util.DateUtil;
import org.devops.util.LogUtil;
import org.devops.util.NetworkUtil;
import org.devops.util.SystemProperties;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebFilter(filterName = "MainFilter", urlPatterns = "/*")
public class MainFilter implements Filter {

    private static Properties systemProperties = SystemProperties.getSystemProperties();
    private static Log log = Logs.get();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        String path = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        System.out.println(path);
        resp.setContentType("text/html; charset=UTF-8");
        boolean isOpenIpWhite = Boolean.valueOf(systemProperties.getProperty("ip_white_open"));
        String ipStr = NetworkUtil.getIpAddress(httpServletRequest);

        String logPattern = ">>> 时间：%s ，IP：%s ，访问：%s ，方法：%s ，拦截：%s \r\n";
        if(isOpenIpWhite && !NetworkUtil.isPass(ipStr)) {
            resp.setContentType("text/plain; charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            LogUtil.log(String.format(logPattern, DateUtil.getCurDateTime(), ipStr, path, method, true));
            writer.println("<h3>无访问权限，请联系管理员</h3>");
        }else{
            LogUtil.log(String.format(logPattern, DateUtil.getCurDateTime(), ipStr, path, method, false));
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("filter init...");
    }

}
