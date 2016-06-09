package simple;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
public class SimpleServiceApplication {
	
	public static void main(String args[]){
		SpringApplication.run(SimpleServiceApplication.class, args);
	}
	
	@Bean
	@RefreshScope
	SimpleController getSimpleController(){
		return new SimpleController();
	}
	
	@Bean
	@RefreshScope
	SimpleObjectRepository createSimpleObjectRepository(){
		SimpleObjectRepository sor = new SimpleObjectRepositoryImpl();
		return sor;
	}
	
	@Bean
	@ConfigurationProperties(prefix="data")
	SimpleObjectConfiguration getSimpleObjectConfiguration(){
		return new SimpleObjectConfiguration();
	}
	

	@Bean
	InitializingBean populateTestData(final SimpleObjectRepository repo, SimpleObjectConfiguration config){
		return new InitializingBean() {
			
			public void afterPropertiesSet() throws Exception {
				config.getConfiguredSimpleObjects().forEach( so -> repo.save(so) );
			}
			
		};
	}


}
