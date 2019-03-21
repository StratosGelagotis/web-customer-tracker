package com.luv2code.springdemo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.luv2code.springdemo")
@PropertySource(
        {"classpath:persistence-mysql.properties",
                "classpath:security-persistence-mysql.properties"})
public class CrmAppConfig implements WebMvcConfigurer {
    // Variable to hold properties
    @Autowired
    private Environment env;
    // Logger for diagnostics
    Logger logger = Logger.getLogger(getClass().getName());

    // Define View Resolver
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver internalResourceViewResolver =
                new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    // Define for Data Source (The one containing customers)
    @Bean
    public DataSource myDataSource(){
        // create connection pool
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        //set jdbc driver
        try {
            dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        // log url and user to make sure we are reading the data
        logger.info(">> jdbc.url: "  + env.getProperty("jdbc.url"));
        logger.info(">> jdbc.user: " + env.getProperty("jdbc.user"));

        // set database connection properties
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        //set database connection pool properties
        dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("connection.pool.initialPoolSize")));
        dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("connection.pool.maxPoolSize")));
        dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("connection.pool.minPoolSize")));
        dataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("connection.pool.maxIdleTime")));
        return dataSource;
    }

    // Handle Hibernate properties
    private Properties getHibernateProperties(){
        //set Hibernate Properties
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect"  , env.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql" , env.getProperty("hibernate.show_sql"));
        return properties;
    }

    // set session factory
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        // create session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        // set the properties
        sessionFactory.setDataSource(myDataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());
        return sessionFactory;
    }

    // Define for Security Data Source (The one containing users, their passwords and their roles)
    @Bean
    public DataSource securityDataSource(){
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
        try {
            securityDataSource.setDriverClass(env.getProperty("security.jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        // log info to make sure we are really reading the data from properties file
        logger.info(">>> security.jdbc.url: " + env.getProperty("security.jdbc.url"));
        logger.info(">>> security.jdbc.user: "   + env.getProperty("security.jdbc.user"));

        // set database connection properties
        securityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
        securityDataSource.setUser(env.getProperty("security.jdbc.user"));
        securityDataSource.setPassword(env.getProperty("security.jdbc.password"));

        // set connection pool properties
        securityDataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("security.connection.pool.initialPoolSize")));
        securityDataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("security.connection.pool.maxPoolSize")));
        securityDataSource.setMinPoolSize(Integer.parseInt(env.getProperty("security.connection.pool.minPoolSize")));
        securityDataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("security.connection.pool.maxIdleTime")));
        return securityDataSource;
    }

    // configure Hibernate Transaction manager
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        // setup transaction manager based on session factory
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(sessionFactory);
        return tx;
    }

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
