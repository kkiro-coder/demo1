package com.example.demo1.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.Validate;

@Component
@Lazy(value = false)
public class SpringContextHelper<T> implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == ctx) ctx = applicationContext;
    }

    public static Object getBeanByName(String beanName) {
        return ctx.getBean(beanName);
    }

    public static <T> T getBeanByClazz(Class<T> requiredType) {
        return ctx.getBean(requiredType);
    }

    public static void clearRef() {
        ctx = null;
    }

    private static void assertCtxInjected() {
        Validate.isTrue(ctx == null, "ctx属性未注入");
    }
}
