package com.atguigu.gmall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunzhipeng
 * @create 2021-03-21 15:39
 * 熟悉SpringBoot处理流程
 */
//标识为controller组件，交给Sprint容器管理，并接收处理请求  如果返回String，会当作网页进行跳转
//@Controller
//RestController = @Controller + @ResponseBody  会将返回结果转换为json进行响应
@Slf4j
@RestController
public class FirstController {
    @RequestMapping("/testDemo")
    public String test(@RequestParam("name") String nn,@RequestParam("age") int age){
        System.out.println(nn+":"+age);
        return "success";
    }
}
