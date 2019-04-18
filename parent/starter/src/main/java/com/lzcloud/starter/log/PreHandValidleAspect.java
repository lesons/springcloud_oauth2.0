package com.lzcloud.starter.log;

import com.lzcloud.common.utils.NewValidated;
import com.lzcloud.common.utils.ValidaUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 自动验证拦截aop
 * Created by lz on 2018-10-18.
 */
@Aspect
@Component
@Slf4j
@AutoConfigureAfter(LogMethodAspect.class)
public class PreHandValidleAspect {


    //    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    @Pointcut("execution(* com.lzcloud..controller ..*.*(..))")
    public void annotationPointCutValidator() {
    }


    @Before("annotationPointCutValidator()")
    public void BeforeValidator(JoinPoint joinPoint) throws Exception {

//
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();

        //TODO 优先使用自动绑定的验证BindingResult
        List<Integer> hasValids = new ArrayList<>();
        Object[] val = joinPoint.getArgs();
        for (int i = 0; i < val.length; i++) {
            if (val[i] instanceof BindingResult) {
                ValidaUtils.beanValidator((BindingResult) val[i]);
                if (i > 0) {
                    hasValids.add(i - 1);
                }
            }
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        for (int i = 0; i < method.getParameters().length; i++) {
            NewValidated validated = method.getParameters()[i].getAnnotation(NewValidated.class);
            if (validated != null && !hasValids.contains(i)) {
                Object value = joinPoint.getArgs()[i];
                if (value != null && value instanceof List && !((List) value).isEmpty()) {
                    ((List) value).stream().forEach(e -> ValidaUtils.beanValidator(e, validated.value()));
                } else {
                    ValidaUtils.beanValidator(value, validated.value());
                }
            }
        }
    }
}
