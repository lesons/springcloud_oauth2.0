package com.lzcloud.starter.log;

import com.lzcloud.common.utils.ThreadsUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @BelongsProject: house-parent-common
 * @BelongsPackage: com.eju.houseparent.starter.log
 * @Author: lzheng
 * @DATE: 2018/12/3 16:15
 * @Description:
 */
@Data
@Slf4j
public class SysLogService {

    DataSource dataSource;
    StarterLogProperties starterLogProperties;

    public SysLogService(DataSource dataSource, StarterLogProperties starterLogProperties) {
        this.starterLogProperties = starterLogProperties;
        this.dataSource = dataSource;
    }

    private static ThreadsUtils threadsUtils = new ThreadsUtils();

    public int insert(SysLog sysLog) {
        threadsUtils.getExecutorService().execute(() -> {
            sysLog.setDeleted(0);
            sysLog.setVersion(1);
            sysLog.setCreateTime(LocalDateTime.now());
            sysLog.setUpdateTime(LocalDateTime.now());
            sysLog.setModule(starterLogProperties.getModule());
            String sql = "INSERT INTO " + starterLogProperties.getTableName() + " ( `user_id`, `user_mobile`, `level`, `operation_code`, `name`, `module`, `result_code`, `result_message`, `request_url`, `request_body`, `request_header`, `response_body`, `add_ip`, `version`, `use_millisecond`, `deleted`, `create_time`, `update_time`,`username`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);";        //插入sql语句
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = dataSource.getConnection();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ps = conn.prepareStatement(sql);

                ps.setObject(1, sysLog.getUserId());
                ps.setObject(2, sysLog.getUserMobile());
                ps.setObject(3, sysLog.getLevel());
                ps.setObject(4, sysLog.getOperationCode());
                ps.setObject(5, sysLog.getName());
                ps.setObject(6, sysLog.getModule());
                ps.setObject(7, sysLog.getResultCode());
                ps.setObject(8, sysLog.getResultMessage());
                ps.setObject(9, sysLog.getRequestUrl());
                ps.setObject(10, sysLog.getRequestBody());
                ps.setObject(11, sysLog.getRequestHeader());
                ps.setObject(12, sysLog.getResponseBody());
                ps.setObject(13, sysLog.getAddIp());
                ps.setObject(14, sysLog.getVersion());
                ps.setObject(15, sysLog.getUseMillisecond());
                ps.setObject(16, sysLog.getDeleted());
                ps.setObject(17, sysLog.getCreateTime());
                ps.setObject(18, sysLog.getUpdateTime());
                ps.setObject(19, sysLog.getUsername());
                ps.executeUpdate();            //执行sql语句
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    ps.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        return 0;
    }
}
