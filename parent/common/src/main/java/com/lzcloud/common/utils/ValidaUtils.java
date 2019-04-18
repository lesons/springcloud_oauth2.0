package com.lzcloud.common.utils;

import com.lzcloud.common.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

/**
 * 验证
 * Created by lz on 2018-10-18.
 */
@Slf4j
public class ValidaUtils {
    public static final String ATTRIBUTE_MESSAGE_PREFIX = "validator.attribute.";

    /**
     * 服务端参数有效性验证
     *
     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
     */
    public static void beanValidator(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            String fieldName = "";
            String realSimpleClassName = "";
            Class curClass = bindingResult.getTarget().getClass();
            String[] fields = list.get(0).getField().split("\\.");
            for (String name : fields) {
                fieldName = name;
                realSimpleClassName = curClass.getSimpleName();
                if (fields.length > 1) {
                    try {
                        curClass = curClass.getMethod("get" + upperCase(fieldName)).getReturnType();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
            String attribute = SpringContextHolder.getMessageNoCode(ATTRIBUTE_MESSAGE_PREFIX + realSimpleClassName + "." + fieldName);
            if (attribute == null) {
                attribute = SpringContextHolder.getMessageNoCode(ATTRIBUTE_MESSAGE_PREFIX + fieldName);
            }
            attribute = StringUtils.isNotBlank(attribute) ? attribute + " " : fieldName + " ";
//            fail(attribute + list.get(0).getDefaultMessage());
        }
    }

    private static ValidatorFactory vf = null;

    /**
     * 服务端参数有效性验证
     * <p>
     * ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
     * vf.getValidator().validate(ladders.get(1));
     *
     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
     */
    public static void beanValidator(Object o, Class<?>... groups) {
        if (vf == null) {
            //TODO 经测试 构建验证Factory  在java -jar 命令启动的情况下 会偶尔出现很卡的现象 暂做为静态构建一次
            vf = Validation.buildDefaultValidatorFactory();
        }

        Set<ConstraintViolation<Object>> constraintViolations = vf.getValidator().validate(o, groups);

        if (!constraintViolations.isEmpty()) {// 入参认证不通过
            ConstraintViolation c = constraintViolations.iterator().next();
            String fieldName = "";
            String realSimpleClassName = "";
            Class curClass = c.getRootBeanClass();
            String[] fields = c.getPropertyPath().toString().split("\\.");
            for (String name : fields) {
                log.debug(name);
                fieldName = name;
                realSimpleClassName = curClass.getSimpleName();
                if (fields.length > 1) {
                    try {
                        curClass = curClass.getMethod("get" + upperCase(fieldName)).getReturnType();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
            String attribute = SpringContextHolder.getMessageNoCode(ATTRIBUTE_MESSAGE_PREFIX + realSimpleClassName + "." + fieldName);
            if (attribute == null) {
                attribute = SpringContextHolder.getMessageNoCode(ATTRIBUTE_MESSAGE_PREFIX + fieldName);
            }
            attribute = StringUtils.isNotBlank(attribute) ? attribute + " " : fieldName + " ";
//            fail(attribute + c.getMessage());
        }
    }

    public static String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
