package com.anodyzed.foobar.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * PersistenceConfig
 *
 * @author Chris Pratt
 * @since 2020-03-08
 */
@Configuration
@EnableJpaRepositories(basePackages="com.anodyzed.foobar.repositories")
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages="com.anodyzed.foobar.repositories")
@PropertySource("classpath:application.properties")
public class PersistenceConfig {
  @Autowired
  private Environment env;

  @Bean
  public DataSource dataSource () {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
    config.setJdbcUrl(env.getRequiredProperty("jdbc.url"));
    config.setUsername(env.getRequiredProperty("jdbc.username"));
    config.setPassword(env.getRequiredProperty("jdbc.password"));
    return new HikariDataSource(config);
  } //dataSource

  @Bean
  public JdbcTemplate jdbcTemplate () {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.setDataSource(dataSource());
    return jdbcTemplate;
  } //jdbcTemplate

  @Bean
  LocalContainerEntityManagerFactoryBean entityManagerFactory () {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("com.anodyzed.foobar.model");
    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.dialect",env.getRequiredProperty("hibernate.dialect"));
    jpaProperties.put("hibernate.hbm2ddl.auto",env.getRequiredProperty("hibernate.hbm2ddl.auto"));
    jpaProperties.put("hibernate.ejb.naming_strategy",env.getRequiredProperty("hibernate.ejb.naming_strategy"));
    jpaProperties.put("hibernate.show_sql",env.getProperty("hibernate.show_sql","false"));
    jpaProperties.put("hibernate.format_sql",env.getProperty("hibernate.format_sql","false"));
    entityManagerFactoryBean.setJpaProperties(jpaProperties);
    return entityManagerFactoryBean;
  } //entityManagerFactory

  @Bean
  JpaTransactionManager transactionManager (EntityManagerFactory entityManager) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManager);
    return txManager;
  } //transactionManager

} //*PersistenceConfig
