package simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleObjectRepositoryImpl implements SimpleObjectRepository{

	private HashMap<String, SimpleObject> simpleObjectMap = new HashMap<String, SimpleObject>();
	
	public SimpleObjectRepositoryImpl(){
		
	}
	
	public List<SimpleObject> findAllSimpleObjects(){
		ArrayList<SimpleObject> retList = new ArrayList<SimpleObject>();
		for(SimpleObject so: simpleObjectMap.values()){
			retList.add(so);
		}
		return retList;
	}
	
	public SimpleObject findByName(String name){
		return simpleObjectMap.get(name);
	}
	
	public void save(SimpleObject so){
		simpleObjectMap.put(so.getName(), so);
	}
}
