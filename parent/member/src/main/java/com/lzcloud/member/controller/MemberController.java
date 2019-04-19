package com.lzcloud.member.controller;

import com.lzcloud.member.FeignMemberServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 〈〉
 *
 * @author lzheng
 * @create 2019/04/17
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
public class MemberController {

    @GetMapping("hello")
    @PreAuthorize("hasAnyAuthority('hello')")
    public String hello(){
        return "hello";
    }

    @Autowired
    FeignMemberServer feignMemberServer;

    @GetMapping("current")
    public String user(Principal principal) {
        return feignMemberServer.feignTest(principal.getName());
    }

    @GetMapping("query")
    @PreAuthorize("hasAnyAuthority('query')")
    public String query() {
        return "具有query权限";
    }
}
