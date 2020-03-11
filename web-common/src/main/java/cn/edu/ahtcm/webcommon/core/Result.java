package cn.edu.ahtcm.webcommon.core;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author liuyu
 */
@Data
public class Result {
    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 成功结果
     * @return
     */
    public static Result ok(){
        return new Result().setCode(HttpStatus.OK.value()).setMessage(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * 自定义成功消息
     * @param message
     * @return
     */
    public static Result ok(String message){
        return new Result().setCode(HttpStatus.OK.value()).setMessage(message);
    }

    /**
     * 携带数据的成功消息
     * @param data
     * @return
     */
    public static Result ok(Object data){
        return new Result().setCode(HttpStatus.OK.value()).setMessage(HttpStatus.OK.getReasonPhrase()).setData(data);
    }

    /**
     * 错误结果
     * @return
     */
    public static Result fail(){
        return new Result().setCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    /**
     * 自定义错误消息
     * @param message
     * @return
     */
    public static Result fail(String message){
        return new Result().setCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage(message);
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
