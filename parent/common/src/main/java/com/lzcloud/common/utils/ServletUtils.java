package com.lzcloud.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lz
 * @description 获取servlet请求与响应信息
 * @since 2017-07-27
 */
public class ServletUtils {
    public static Map<String, String> parseHeaderMap(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        Enumeration names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement().toString();
            String value = request.getHeader(name);
            headerMap.put(name, value);
        }
        return headerMap;
    }

    public static void writerResponseBody(HttpServletResponse response, String responseBody) {
        try {
            response.getWriter().append(responseBody);
        } catch (IOException e) {
            throw new RuntimeException("输出[" + responseBody + "]给[" + response + "]时发生异常:" + e.getMessage(), e);
        }
    }

    public static void setResponseHeader(HttpServletResponse response, Map<String, String> responseHeader) {
        for (Map.Entry<String, String> entry : responseHeader.entrySet()) {
            response.addHeader(entry.getKey(), entry.getValue());
        }
    }

    public static String getBodyByRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String getRemoteIp(HttpServletRequest request) {
        //取代理ip地址
        String ip = request.getHeader("x-forwarded-for");
        //取nginx代理设置的ip地址
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        //从网上取的
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        //取JAVA获得的ip地址
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //去除unkonwn
        if (ip.startsWith("unknown")) {
            ip = ip.substring(ip.indexOf("unknown") + "unknown".length());
        }
        //去除多多余的信息
        ip = ip.trim();
        if (ip.startsWith(",")) {
            ip = ip.substring(1);
        }
        if (ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    public static String getParameterMap(HttpServletRequest req) {
        return JSONObject.toJSONString(req.getParameterMap());
    }


    /**
     * 获取request对象
     *
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
        return servletRequest;
    }
}
