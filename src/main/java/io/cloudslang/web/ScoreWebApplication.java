package io.cloudslang.web;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 1:09 PM
 */

import io.cloudslang.lang.api.Slang;
import io.cloudslang.lang.api.SlangImpl;
import io.cloudslang.lang.api.configuration.SlangSpringConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Import(SlangSpringConfiguration.class)
@ImportResource("spring/slangWebappContext.xml")
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "io.cloudslang.web")
public class ScoreWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreWebApplication.class, args);
    }

    @Bean
    public Slang getSlang(){
        return new SlangImpl();
    }
}
