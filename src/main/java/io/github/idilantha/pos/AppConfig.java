package io.github.idilantha.pos;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(HibernateConfig.class)
@ComponentScan(basePackages = {"io.github.idilantha.pos"})
@Configuration
public class AppConfig {

}
