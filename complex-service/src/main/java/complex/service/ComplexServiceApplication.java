package complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;

//import com.netflix.appinfo.InstanceInfo;
//import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import simple.SimpleObject;


@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@EnableHystrix
@EnableCircuitBreaker
@EnableFeignClients
public class ComplexServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComplexServiceApplication.class, args);
    }
    
	@Bean
	Controller getController(){
		return new Controller();
	}
	
	@Bean
	@RefreshScope
	ComplexObjectRepository createSimpleObjectRepository(){
		ComplexObjectRepository sor = new ComplexObjectRepositoryImpl();
		return sor;
	}
	
	@Bean
	SimpleObjectClient createSimpleObjectClient(){
		return new SimpleObjectClient();
	}
	

	@Bean
	InitializingBean populateTestData(final ComplexObjectRepository repo, ComplexConfiguration config){
		return new InitializingBean() {
			public void afterPropertiesSet() throws Exception {
				config.getConfiguredComplexObjects().forEach( so -> repo.save(so) );
			}
			
		};
	}
	
	@Bean
	@ConfigurationProperties(prefix="complex-data")
	ComplexConfiguration getConfiguration(){
		return new ComplexConfiguration();
	}
    
 	
}

class ComplexConfiguration{
	private List<String> owners = new ArrayList<String>();
	
	public List<String> getOwners() {
		return owners;
	}
	
	public void setOwners(List<String> owners) {
		this.owners = owners;
	}
	
	public List<ComplexObject> getConfiguredComplexObjects(){
		return owners.stream()
				.map(s -> convert(s) )
				.collect(Collectors.toList());
	}
	
	private ComplexObject convert(String line){
		String elem[] = line.split(":");
		String name = (elem.length>0)?elem[0]:"NULL";
		int lvl = 0;
		try{ lvl = Integer.parseInt((elem.length>1)?elem[1]:"0"); }
		catch(Exception e){}
		return new ComplexObject(name, lvl);
	}
}

@RestController
@RequestMapping("/complex")
class Controller {
	
	@Autowired
	private SimpleObjectClient simpleObjectClient;

	@Autowired
	private ComplexObjectRepository complexObjectRepo = null;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<ComplexObject> getList(){
		List<ComplexObject> rlist =  complexObjectRepo.findAllComplexObjects();
		for(ComplexObject co:rlist)
			simpleObjectClient.populateSimpleObject(co);
		return rlist;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "ownerName/{ownerName}")
	public ComplexObject getByName(@PathVariable String ownerName){
		//here is where the switching logic is.
		return simpleObjectClient.populateSimpleObject(complexObjectRepo.findByOwnerName(ownerName));
	}
	
}

@Component
class SimpleObjectClient{
	
	//@Autowired
	//private DiscoveryClient discoveryClient;
	
	@Autowired
	private SimpleObjectRestClient restClient;

	@HystrixCommand(fallbackMethod = "defaultPopulate")
	public ComplexObject populateSimpleObject(ComplexObject co){
		if(co==null) return co;
		co.setSimpleObject(null);
		/*
		InstanceInfo instance = discoveryClient.getNextServerFromEureka("simple-service", false);
		String simpleServiceUrl = instance.getHomePageUrl();
		String url = UriComponentsBuilder.fromHttpUrl(String.format("%s/simple/name/%s", simpleServiceUrl, co.getOwnerName()))
				.buildAndExpand().toUriString();
		
		RestTemplate restTemplate = new RestTemplate();
		SimpleObject so = restTemplate.getForObject(url, SimpleObject.class);
		*/
		
		SimpleObject so = restClient.getObjectByName(co.getOwnerName());
		co.setSimpleObject(so);
		return co;
	}
	
	public ComplexObject defaultPopulate(ComplexObject co){
		if(co!=null){
			co.setSimpleObject(null);
		}
		return co;
	}
	
}

@Component
@FeignClient("simple-service")
interface SimpleObjectRestClient {
	@RequestMapping(method = RequestMethod.GET, value="simple/name/{name}")
	SimpleObject getObjectByName(@PathVariable("name") String name);
}

class ComplexObject{
	private SimpleObject simpleObject;
	private String ownerName;
	private int ownershipLevel;
	
	public ComplexObject(){}
	public ComplexObject(String name, int level){
		this.ownerName = name;
		this.ownershipLevel = level;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	public int getOwnershipLevel() {
		return ownershipLevel;
	}
	public SimpleObject getSimpleObject() {
		return simpleObject;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public void setOwnershipLevel(int ownershipLevel) {
		this.ownershipLevel = ownershipLevel;
	}
	public void setSimpleObject(SimpleObject simpleObject) {
		this.simpleObject = simpleObject;
	}
	
}

@Repository
interface ComplexObjectRepository{
	List<ComplexObject> findAllComplexObjects();
	ComplexObject findByOwnerName(String ownerName);
	void save(ComplexObject so);
}

class ComplexObjectRepositoryImpl implements ComplexObjectRepository{
	
	private HashMap<String, ComplexObject> complexObjectMap = new HashMap<String, ComplexObject>();
	
	public List<ComplexObject> findAllComplexObjects(){
		ArrayList<ComplexObject> retList = new ArrayList<ComplexObject>();
		for(ComplexObject so: complexObjectMap.values()){
			retList.add(so);
		}
		return retList;
	}
	public ComplexObject findByOwnerName(String ownerName){
		return complexObjectMap.get(ownerName);
	}

	public void save(ComplexObject so){
		complexObjectMap.put(so.getOwnerName(), so);
	}
	
}



