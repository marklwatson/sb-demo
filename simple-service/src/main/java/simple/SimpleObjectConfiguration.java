package simple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="data")
public class SimpleObjectConfiguration {

	private List<String> records = new ArrayList<String>();
	
	public List<String> getRecords() {
		return records;
	}
	
	public void setRecords(List<String> records) {
		this.records = records;
	}
	
	public List<SimpleObject> getConfiguredSimpleObjects(){
		return records.stream()
				.map(s -> convert(s) )
				.collect(Collectors.toList());
	}
	
	private SimpleObject convert(String line){
		String elem[] = line.split(":");
		String name = (elem.length>0)?elem[0]:"NULL";
		int age = 0;
		try{ age = Integer.parseInt((elem.length>1)?elem[1]:"0"); }
		catch(Exception e){}
		return new SimpleObject(name, age);
	}
	
}
