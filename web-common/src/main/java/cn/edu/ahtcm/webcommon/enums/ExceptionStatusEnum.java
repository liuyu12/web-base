package cn.edu.ahtcm.webcommon.enums;

import lombok.Data;

/**
 * @author liuyu
 */
public enum ExceptionStatusEnum {
    /**
     * 系统内部错误-用户不存在
     */
    USER_NOT_EXISTS(501,"用户不存在");
    ;
    /**
     * 错误代码
     */
    private Integer code;

    /**
     * 错误描述
     */
    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ExceptionStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
