package cn.edu.ahtcm.codegenerator;

import cn.edu.ahtcm.webcommon.configure.WebMvcConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author liuyu
 */
@Import({WebMvcConfigure.class})
@SpringBootApplication
public class CodeGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeGeneratorApplication.class, args);
    }

}
