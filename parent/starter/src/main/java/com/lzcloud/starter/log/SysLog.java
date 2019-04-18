package com.lzcloud.starter.log;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统操作日志
 * </p>
 *
 * @author lz
 * @since 2018-10-29
 */
@Data
@Slf4j
public class SysLog {

    /**
     * 用户id
     */
    private Long id;
    private Long userId;
    private String username;
    private String userMobile;
    private String level;
    private String operationCode;
    /**
     * 请求接口名称
     */
    private String name;
    /**
     * 请求接口代码
     */
    private String module;
    /**
     * 交互结果标识
     */
    private String resultCode;
    /**
     * 交互结果信息
     */
    private String resultMessage;
    private String requestUrl;
    /**
     * 请求参数
     */
    private String requestBody;
    /**
     * 请求头
     */
    private String requestHeader;
    /**
     * 返回结果信息
     */
    private String responseBody;
    private String addIp;

    /**
     * 访问时长
     */
    private Long useMillisecond;
    protected Integer version;
    protected LocalDateTime createTime;
    protected LocalDateTime updateTime;

    protected Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }

    public Long getUseMillisecond() {
        return useMillisecond;
    }

    public void setUseMillisecond(Long useMillisecond) {
        this.useMillisecond = useMillisecond;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

}
