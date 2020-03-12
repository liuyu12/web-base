package cn.edu.ahtcm.webtest;

import cn.edu.ahtcm.webcommon.annotation.EnableTkMybatis;
import cn.edu.ahtcm.webcommon.annotation.EnableWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuyu
 */
@EnableWeb
@EnableTkMybatis
@SpringBootApplication
public class WebTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebTestApplication.class, args);
    }

}
