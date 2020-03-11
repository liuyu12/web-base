package cn.edu.ahtcm.webcommon.annotation;

import cn.edu.ahtcm.webcommon.configure.MybatisConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author liuyu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MybatisConfigurer.class)
@Documented
public @interface EnableTkMybatis {
}
