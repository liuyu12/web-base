package cn.edu.ahtcm.codegenerator.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liuyu
 */
@Data
public class ProjectBean {
    @NotBlank
    private String position;
    @NotBlank
    private String basePackage;
    @NotBlank
    private String modulePackage;
    @NotBlank
    private String entityPackage;
    @NotBlank
    private String servicePackage;
    @NotBlank
    private String serviceImplPackage;
    @NotBlank
    private String mapperPackage;
    @NotBlank
    private String mapperXmlPackage;
    @NotBlank
    private String controllerPackage;
    @NotBlank
    private String author = "liuyu";
}
