package cn.edu.ahtcm.codegenerator.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liuyu
 */
@Data
public class DataBaseBean {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String url;
    @NotBlank
    private String tables;
    private boolean alias;
    private String aliasName;
}
