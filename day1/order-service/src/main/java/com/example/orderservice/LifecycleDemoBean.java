package com.example.orderservice;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class LifecycleDemoBean implements InitializingBean, DisposableBean {

    public LifecycleDemoBean() {
        System.out.println("[LifecycleDemoBean]: Constructor called.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[LifecycleDemoBean]: InitializingBean.afterPropertiesSet() called.");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("[LifecycleDemoBean]: @PostConstruct method called. Dependencies are injected.");
    }

    public void businessMethod() {
        System.out.println("[LifecycleDemoBean]: Business method executed.");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("[LifecycleDemoBean]: @PreDestroy method called. Releasing resources.");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("[LifecycleDemoBean]: DisposableBean.destroy() called.");
    }
}
