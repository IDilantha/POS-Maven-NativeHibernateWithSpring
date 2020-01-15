package io.github.idilantha.pos;

import lk.ijse.dep.crypto.DEPCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("file:${user.dir}/resources/application.properties")
public class HibernateConfig {

    @Autowired
    Environment env;

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
        properties.put();
        properties.put();
        properties.put();
        properties.put();
        return properties;
    }
}
