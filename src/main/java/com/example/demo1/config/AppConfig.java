package com.example.demo1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo1.repo",
        repositoryFactoryBeanClass = com.example.demo1.repo.factory.BaseRepositoryFactoryBean.class)
@EnableTransactionManagement
public class AppConfig {
}
