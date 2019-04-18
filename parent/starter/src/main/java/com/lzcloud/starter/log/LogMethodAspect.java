package com.lzcloud.starter.log;

import com.alibaba.fastjson.JSONObject;
import com.lzcloud.common.utils.JSONUtils;
import com.lzcloud.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;

/**
 * Created by lz on 2018-10-18.
 */
@Slf4j
@Aspect
@Component
public class LogMethodAspect {

    public static final String SUCESS = "sucess";
    public static final String ERROR = "error";
    public static final String WARN = "warn";

    public static ThreadLocal<SysLog> sysLogThreadLocal = new ThreadLocal<>();

    @Autowired
    private StarterLogProperties starterLogProperties;

    @Autowired
    SysLogService sysLogService;
    @Autowired
    UserInfoInterface userInfoInterface;

    @Pointcut("execution(* com.lzcloud..controller ..*.*(..))")
    public void annotationPointCutLog() {
    }

    public static String getParameterMap(HttpServletRequest req) {
        return JSONObject.toJSONString(req.getParameterMap());
    }

    @Before("annotationPointCutLog()")
    public void BeforeLog(JoinPoint joinPoint) {
        SysUser sysUser = userInfoInterface.getUserInfo();
        if (sysUser == null)
            sysUser = new SysUser();
        HttpServletRequest request = ServletUtils.getCurrentRequest();
        SysLog sysLog = new SysLog();
        sysLog.setAddIp(ServletUtils.getRemoteIp(request));
        sysLog.setRequestUrl(request.getRequestURI());
        sysLog.setRequestHeader(JSONUtils.toJSONString(ServletUtils.parseHeaderMap(request)));
        sysLog.setRequestBody(getParameterMap(request));
        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setUserMobile(sysUser.getUserMobile());
        sysLog.setUserId(sysUser.getUserId());
        sysLog.setUsername(sysUser.getUsername());
//        if (SecurityContextHolder.getContext().getAuthentication() != null) {
//            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
//                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                if (user != null) {
//                    sysLog.setUserId(user.getId());
//                    sysLog.setUserMobile(user.getMobile());
//                }
//            }
//        }


        sysLogThreadLocal.set(sysLog);
    }


    @AfterReturning(pointcut = "annotationPointCutLog()", returning = "result")
    public void afterReturnExcute(JoinPoint joinPoint, Object result) {
        SysLog sysLog = sysLogThreadLocal.get();
        if (sysLog == null) {
            return;
        }
        sysLog.setUpdateTime(LocalDateTime.now());
        sysLog.setUseMillisecond(sysLog.getUpdateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli() - sysLog.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        sysLog.setLevel(SUCESS);
        sysLog.setResponseBody(JSONUtils.toJSONString(result));
//        sysLogMapper.insert(sysLog);
        sysLogService.insert(sysLog);
    }


    //后置异常通知
    @AfterThrowing(value = "annotationPointCutLog()", throwing = "e")
    public void throwss(JoinPoint joinPoint, Exception e) {
        SysLog sysLog = sysLogThreadLocal.get();
        if (sysLog == null) {
            return;
        }
        sysLog.setUpdateTime(LocalDateTime.now());
        sysLog.setUseMillisecond(sysLog.getUpdateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli() - sysLog.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        sysLog.setLevel(ERROR);
        sysLog.setResultCode("1");
        sysLog.setResultMessage("操作错误");
        sysLog.setResponseBody(getMsg(e));
        sysLogService.insert(sysLog);
    }

    public String getMsg(Exception e) {
        String errorMsg = e.toString() + "      " + e.getMessage();
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        for (int i = 0; i < ((stackTraceElements.length > 20) ? 20 : stackTraceElements.length); i++) {
            errorMsg += " \n        " + stackTraceElements[i].toString();
        }
        return errorMsg;
    }
}

