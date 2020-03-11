package cn.edu.ahtcm.webcommon.exception;

import cn.edu.ahtcm.webcommon.enums.ExceptionStatusEnum;

/**
 * @author liuyu
 */
public class ServiceException extends RuntimeException{
    /**
     * 错误代码
     */
    private int code;

    public ServiceException(ExceptionStatusEnum statusEnum){
        super(statusEnum.getDesc());
        this.code = statusEnum.getCode();
    }

    public ServiceException(String message){
        super(message);
    }

}
