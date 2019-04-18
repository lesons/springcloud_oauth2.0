package com.lzcloud.common;

import com.lzcloud.common.entity.BaseDTO;
import com.lzcloud.common.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author lz
 * @version 2018-10-26
 * 控制层统一处理
 */
@RestControllerAdvice
public class BaseControllerHandler implements ResponseBodyAdvice {
    private static final Logger log = LoggerFactory.getLogger(BaseControllerHandler.class);

    private BaseDTO getResult(String message) {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setCode("0");
        baseDTO.setMsg(message);
        return baseDTO;
    }

    private BaseDTO getResult(String code, String message) {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setCodeAndMsg(code, message);
        return baseDTO;
    }


    /**
     * MissingServletRequestParameterException 约定参数错误，
     * HttpRequestMethodNotSupportedException method方法类型错误
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            HttpMediaTypeNotSupportedException.class,
            TypeMismatchException.class})
    @ResponseBody
    public BaseDTO handle400Exception(HttpServletRequest req, HttpServletResponse resp, Exception exception) {
        log.warn("handleSupportedException parameter info:{}==={}", req.getRequestURI(),ServletUtils.getParameterMap(req));
        log.warn("handleSupportedException ex info:{}==={}", exception.getClass(), exception.getMessage());
        resp.setStatus(400);
        if (exception instanceof HttpMessageNotReadableException) {
            log.warn("body参数无法解析,请验证JSON参数格式");
            return getResult("400", "body参数无法解析,请验证JSON参数格式");
        }
        return getResult("400", exception.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public BaseDTO noHandlerFoundException(HttpServletRequest req, HttpServletResponse resp, Exception exception) {
        log.warn("noHandlerFoundException 404 info:{}", req.getRequestURI());
        log.warn("noHandlerFoundException parameter info" +ServletUtils.getParameterMap(req));
        resp.setStatus(404);
        return getResult("404", "找不到请求方法");
    }


    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class, Exception.class})
    @ResponseBody
    public BaseDTO handleException(HttpServletRequest req, HttpServletResponse resp, Exception exception) {
        exception.printStackTrace();
        log.error("handleException parameter info:{}=={}", req.getRequestURI(),ServletUtils.getParameterMap(req));
        log.error("handleException ex info:{}=={}", exception.getClass(), exception.getMessage());
        //业务服务异常
        resp.setStatus(500);
        return getResult("500", "系统正在开小差，请稍候再试");
    }

    /*@ModelAttribute
    public UserInfo getUserInfo(HttpSession session){
        Object userInfo = session.getAttribute(SESSION_KEY1_ACTIVE_EMPLOYEE);
        return userInfo==null?null:(UserInfo) userInfo;
    }
    */
    @ModelAttribute
    public BaseDTO getBaseDTO() {
        return new BaseDTO();
    }


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getMethod().getName().equals("ssoLogin")
                && !returnType.getMethod().getName().equals("verify")
                && !returnType.getMethod().getName().equals("loginOut")
                && !returnType.getMethod().getName().equals("scope")
                && !returnType.getMethod().getName().equals("ticket");
    }


    @Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter,
                                  MediaType mediaType, Class clas, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        return returnValue;
    }
}
