package com.f.bilibili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @ClassName: FBilibiliApp
 * @Description: 入口
 * @author: XU
 * @date: 2022年04月25日 17:22
 **/
@SpringBootApplication
public class FBilibiliApp {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(FBilibiliApp.class, args);
    }
}
