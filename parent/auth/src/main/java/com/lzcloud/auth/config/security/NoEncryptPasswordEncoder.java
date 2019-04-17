package com.lzcloud.auth.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 〈自定义无加密密码验证〉
 *
 * @author lzheng
 * @create 2019/04/13
 * @since 1.0.0
 */
public class NoEncryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return (String) charSequence;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals((String) charSequence);
    }
}
