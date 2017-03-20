package com.company.airport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.company.airport.ui.MainFrame;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.*;
import java.util.Properties;

/**
 * Created by Антон on 09.03.2017.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.company.airport.repositori")
@EnableTransactionManagement
public class Application implements CommandLineRunner {
    @Autowired
    private ApplicationContext springContext;
    @Override
    public void run(String... args) throws Exception {
        SwingUtilities.invokeLater(()-> {
            MainFrame mainFrame = (MainFrame) springContext.getBean("mainFrame");
            mainFrame.setVisible(true);
        });
    }
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/airport?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
                .driverClassName("com.mysql.jdbc.Driver")
                .type(DriverManagerDataSource.class)
                .username("root")
                .password("root")
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        entityManagerFactoryBean.setPackagesToScan("com.company.airport.model");
        return entityManagerFactoryBean;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }


    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .headless(false)
                .build()
                .run(args);
    }
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;

    }
}
