package io.github.idilantha.pos;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("file:${user.dir}/resources/application.properties")
public class HibernateConfig {

    public DataSource dataSource(){
        DriverManagerDataSource dmds = new DriverManagerDataSource();
        dmds.setDriverClassName();
        dmds.setUsername();
        dmds.setPassword();
        dmds.setUrl();
        return dmds;
    }

    private Properties hibernateproperties(){
        Properties properties = new Properties();
        properties.put();
        properties.put();
        properties.put();
        properties.put();
        return properties;
    }
}
