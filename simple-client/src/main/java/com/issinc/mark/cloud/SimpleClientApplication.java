package com.issinc.mark.cloud;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration
@EnableEurekaClient
public class SimpleClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleClientApplication.class, args);
	}
}

@RestController
class Controller {
	
	@Autowired
	private ComplexObjectRestClient complexObjectRestClient;

	@RequestMapping(method = RequestMethod.GET, value = "complex-service/ownerName/{ownerName}")
	public Map<String, Object> getByName(@PathVariable String ownerName){
		
		Map<String, Object> cobj =  complexObjectRestClient.getObjectByName(ownerName);
		
		return cobj;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "complex-service")
	public List<Map<String, Object>> getList(){
		
		List<Map<String, Object>> cobjList =  complexObjectRestClient.getObjectList();
		
		return cobjList;
	}
	
}

@Component
@FeignClient("complex-service")
interface ComplexObjectRestClient {
	@RequestMapping(method = RequestMethod.GET, value="complex/ownerName/{name}")
	Map<String, Object> getObjectByName(@PathVariable("name") String name);
	
	@RequestMapping(method = RequestMethod.GET, value="complex/")
	List<Map<String, Object>> getObjectList();
	
}
