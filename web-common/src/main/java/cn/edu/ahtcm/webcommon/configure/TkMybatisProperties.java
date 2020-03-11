package cn.edu.ahtcm.webcommon.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author liuyu
 */
@ConfigurationProperties(prefix = "tk.mybatis",ignoreInvalidFields = true)
public class TkMybatisProperties {
    private String basePackage;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
