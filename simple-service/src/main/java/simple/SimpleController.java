package simple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple")
public class SimpleController {

	@Autowired
	private SimpleObjectRepository simpleObjectRepo = null;
	
	public SimpleController(){
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<SimpleObject> getList(){
		return simpleObjectRepo.findAllSimpleObjects();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "name/{name}")
	public SimpleObject getByName(@PathVariable String name){
		return simpleObjectRepo.findByName(name);
	}
	
}
