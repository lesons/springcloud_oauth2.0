package com.lzcloud.member;

import com.lzcloud.member.config.OAuth2FeignConguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @BelongsProject: lzcloud
 * @BelongsPackage: com.lzcloud.member
 * @Author: lzheng
 * @DATE: 2019/4/19 9:45
 * @Description:
 */
@FeignClient(value = "auth",configuration = {OAuth2FeignConguration.class})
public interface FeignMemberServer {

    @GetMapping("/api/feignTest")
    public String feignTest(@RequestParam("name") String name);
}
