package simple;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface SimpleObjectRepository {

	public List<SimpleObject> findAllSimpleObjects();
	
	public SimpleObject findByName(String name);
	
	public void save(SimpleObject so);
	
}
