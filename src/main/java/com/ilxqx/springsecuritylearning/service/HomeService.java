package com.ilxqx.springsecuritylearning.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author venus
 * @version 1.0.0
 * @since 2022/7/27 星期三 20:55
 */
@Service
public class HomeService {

    @PreAuthorize("hasRole('ADMIN')")
    public String sayHello() {
        return "Hello Home!";
    }
}
