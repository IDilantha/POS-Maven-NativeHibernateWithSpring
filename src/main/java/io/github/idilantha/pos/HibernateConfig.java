package io.github.idilantha.pos;

import lk.ijse.dep.crypto.DEPCrypt;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@PropertySource("file:${user.dir}/src/main/resources/application.properties")
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean lsbf = new LocalSessionFactoryBean();
        lsbf.setDataSource(dataSource);
        lsbf.setPackagesToScan("io.github.idilantha.pos.entity");
        lsbf.setHibernateProperties(hibernateproperties());
        return lsbf;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dmds = new DriverManagerDataSource();
        dmds.setDriverClassName(env.getRequiredProperty("hibernate.connection.driver_class"));
        dmds.setUsername(DEPCrypt.decode(env.getRequiredProperty("hibernate.connection.username"),"123"));
        dmds.setPassword(DEPCrypt.decode(env.getRequiredProperty("hibernate.connection.password"),"123"));
        dmds.setUrl(env.getRequiredProperty("hibernate.connection.url"));
        return dmds;
    }

    private Properties hibernateproperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect",env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql",env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto",env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.allow_refresh_detached_entity",env.getRequiredProperty("hibernate.allow_refresh_detached_entity"));
        return properties;
    }

    @Bean
    PlatformTransactionManager transactionManager(SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }
}
