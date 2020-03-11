package cn.edu.ahtcm.webcommon.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 切面抽象类
 */
public abstract class AspectSupport {

    /**
     * 根据切点获取方法
     * @param proceedingJoinPoint
     * @return
     */
    public Method resolveMethod(ProceedingJoinPoint proceedingJoinPoint){
        // 获取方法签名
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        // 获取目标对象和字节码
        Object target = proceedingJoinPoint.getTarget();
        Class<?> targetClass = target.getClass();
        // 通过签名获取方法
        Method method = getDeclaredMethod(targetClass,signature.getName(),signature.getMethod().getParameterTypes());
        if(method == null){
            throw new IllegalStateException("无法解析目标方法："+signature.getMethod().getName());
        }
        return method;
    }

    /**
     * 获取方法名称
     * @param clazz
     * @param name
     * @param parameterTypes
     * @return
     */
    private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethod(superClass, name, parameterTypes);
            }
        }
        return null;
    }
}
