package com.lzcloud.common.entity;

import lombok.Data;

/**
 * 〈响应实体〉
 *
 * @author lzheng
 * @create 2019/04/13
 * @since 1.0.0
 */
@Data
public class Result {

    private int code;
    private String message;
    private Object data;

}
