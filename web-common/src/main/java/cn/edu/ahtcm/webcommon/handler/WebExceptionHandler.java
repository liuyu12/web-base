package cn.edu.ahtcm.webcommon.handler;

import cn.edu.ahtcm.webcommon.core.Result;
import cn.edu.ahtcm.webcommon.exception.ServiceException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author liuyu
 */
@RestControllerAdvice
public class WebExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler({ServiceException.class,RuntimeException.class, MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class})
    public Result handlerBusinessException(Exception e) {
        return Result.fail(e.getMessage());
    }

    /**
     * 处理参数校验异常 json 方式传参
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder messages = new StringBuilder();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            String field = error.getObjectName();
            String message = error.getDefaultMessage();
            messages.append(field).append(message).append(";");
        }
        return Result.fail(messages.toString());
    }

    /**
     * 处理参数校验异常 非json方式传参
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result handlerBindException(BindException e) {
        StringBuilder messages = new StringBuilder();
        List<FieldError> errors = e.getFieldErrors();
        for (FieldError error : errors) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            messages.append(field).append(message).append(";");
        }
        return Result.fail(messages.toString());
    }

    /**
     * 处理参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handlerConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder messages = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String field = constraintViolation.getPropertyPath().toString();
            messages.append(field + constraintViolation.getMessage()).append(";");
        }
        return Result.fail(messages.toString());
    }

}
