package com.issinc.mark;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandidateController {

	@Autowired
	private CandidateRepo repo;
	
	@RequestMapping("/candidate")
	public List<Candidate> findAll(){
		return repo.findAll();
	}
	
	@RequestMapping("/candidate/name/{name}")
	public Candidate findByName(@PathVariable(value="name") String name){
		return repo.findByName(name);
	}
	
	@RequestMapping("/candidate/party/{party}")
	public List<Candidate> findByParty(@PathVariable(value="party") String party){
		return repo.findByParty(party);
	}
	
	@RequestMapping(value="/candidate", method=RequestMethod.PUT)
	public void add(@RequestBody Candidate candidate){
		String name = candidate.getName();
		Candidate ec = repo.findByName(name);
		if(ec!=null){
			candidate.setId(ec.getId());
		}
		repo.save(candidate);
	}
	
}
