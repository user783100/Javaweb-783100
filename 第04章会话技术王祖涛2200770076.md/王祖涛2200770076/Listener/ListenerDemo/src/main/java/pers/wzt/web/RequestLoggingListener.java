package pers.wzt.web;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestLoggingListener implements ServletRequestListener {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        // 记录请求开始的日志
        log("Request started: " + getLogInfo(request));
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;

        // 记录请求结束的日志
        log("Request finished: " + getLogInfo(request) + ", Processing time: " + processingTime + "ms");
    }

    private String getLogInfo(HttpServletRequest request) {
        return "Time: " + DATE_FORMAT.format(new Date()) +
                ", Client IP: " + request.getRemoteAddr() +
                ", Method: " + request.getMethod() +
                ", URI: " + request.getRequestURI() +
                ", QueryString: " + request.getQueryString() +
                ", User-Agent: " + request.getHeader("User-Agent");
    }

    private void log(String message) {
        System.out.println(message); // 这里使用了标准输出，实际部署时可以替换为日志框架如 SLF4J 或 Log4j
    }
}