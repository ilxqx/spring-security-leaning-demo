package com.ilxqx.springsecuritylearning.controller;

import com.ilxqx.springsecuritylearning.pojo.JsonResult;
import com.ilxqx.springsecuritylearning.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页控制器
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 23:34
 */
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping(value = "/index", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult<String> index() {
        return JsonResult.success("成功", this.homeService.sayHello());
    }
}
