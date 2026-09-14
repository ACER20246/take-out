package com.sky.annotation.Aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 前置通知
     */
    @Before("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始动态填充公共字段...");
        //获取数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获取方法上的注解
        OperationType operationType = autoFill.value();
        //获取被拦截方法的参数
        Object[] args = joinPoint.getArgs(); //获取所有对象
        if(args == null || args.length == 0) {
            return;
        }
        //准备赋值的数据
        Object entity = args[0];
        LocalDateTime now = LocalDateTime.now();
        //赋值

        if(operationType == OperationType.INSERT){
            // 设置创建时间和更新时间
            Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            setCreateTime.invoke(entity, now);
            setUpdateTime.invoke(entity, now);
        }else{
            // 设置更新时间
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            setUpdateTime.invoke(entity, now);
        }
    }
}
