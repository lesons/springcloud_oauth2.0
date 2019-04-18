/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.lzcloud.common.utils;

import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

/**
 * json相关工具类.
 *
 * @author lz
 * @version 2018-10-26
 */
@Slf4j
public class JSONUtils {
    public static String toJSONString(Object o) {
        if (o == null) return null;

        String str = com.alibaba.fastjson.JSONObject.toJSONString(o, SerializerFeature.PrettyFormat, SerializerFeature.BrowserCompatible, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
        return str;
    }
}
