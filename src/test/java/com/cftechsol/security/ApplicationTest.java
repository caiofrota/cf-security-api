package com.cftechsol.security;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Application test.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan({"com.cftechsol"})
@EnableJpaRepositories("com.cftechsol")
@EntityScan("com.cftechsol")
public class ApplicationTest {

}
