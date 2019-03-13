package ru.job4j.carprice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Configuration class for application.
 */
@EnableWebMvc
@Configuration
@ComponentScan("ru.job4j.carprice")
@EnableJpaRepositories("ru.job4j.carprice.persistence.repository")
@EnableTransactionManagement
public class Config implements WebMvcConfigurer {

    /**
     * Set up resource handler for handle static content(html, js, css).
     * @param registry resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/");
    }

    /**
     * Set up view controllers.
     * Mapping URL of controller to html page.
     * @param registry view controller registry.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/add").setViewName("add");
        registry.addViewController("/update").setViewName("update");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registration").setViewName("registration");
    }

    /**
     * Set up view resolver bean for html pages.
     * @return view resolver.
     */
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/static/html/");
        resolver.setSuffix(".html");
        return resolver;
    }

    /**
     * Set up transaction manager bean.
     * @return transaction manager.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory());
        return transactionManager;
    }

    /**
     * Set up EntityManagerFactory bean.
     * It's singleton by default.
     * @return entityManagerFactory.
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence
                .createEntityManagerFactory("car_price");
    }
}
