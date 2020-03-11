package cn.edu.ahtcm.webcommon.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 记录controller的请求
 * @author liuyu
 */
@Slf4j
@Aspect
@Component
public class ControllerAspect extends AspectSupport {

    /**
     * Pointcut
     * 格式
     *execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
     * 修饰符匹配（modifier-pattern?）
     * 返回值匹配（ret-type-pattern）可以为*表示任何返回值,全路径的类名等
     * 类路径匹配（declaring-type-pattern?）
     * 方法名匹配（name-pattern）可以指定方法名 或者 *代表所有, set* 代表以set开头的所有方法
     * 参数匹配（(param-pattern)）可以指定具体的参数类型，多个参数间用“,”隔开，各个参数也可以用“*”来表示匹配任意类型的参数，如(String)表示匹配一个String参数的方法；(*,String) 表示匹配有两个参数的方法，第一个参数可以是任意类型，而第二个参数是String类型；可以用(..)表示零个或多个任意参数
     * 异常类型匹配（throws-pattern?）
     * 其中后面跟着“?”的是可选项
     */
    @Pointcut("execution(public * cn.edu.ahtcm..*.*Controller.*(..))")
    public void pointcut(){ }

    /**
     * 环绕通知
     * @param point
     * @return
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String url = attributes.getRequest().getRequestURI().toString();
        long start = System.currentTimeMillis();
        try{
            // 执行方法前
            log.debug("请求接口{}，时间：{}",url,new Date());
            result = point.proceed();
            long end = System.currentTimeMillis();
            // 执行方法后
            log.debug("请求接口{}结束,请求结果：{},用时：{}毫秒",url, JSON.toJSON(result),end-start);
            return result;
        }catch (Throwable throwable){
            // 方法出现异常
            log.error("接口{}出现异常，异常消息为:{}",url,throwable.getMessage());
            throw  throwable;
        }
    }

}
