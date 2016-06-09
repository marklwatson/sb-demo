package simple.zuul;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Controller
@EnableZuulProxy
public class SimpleZuulApplication {
	
	@Bean
	ServerProperties createServerProperties(){
		return new ServerProperties();
	}

    public static void main(String[] args) {
    	new SpringApplicationBuilder(SimpleZuulApplication.class).web(true).run(args);
    }
}
