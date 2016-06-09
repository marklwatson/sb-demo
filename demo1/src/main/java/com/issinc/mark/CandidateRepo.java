package com.issinc.mark;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidateRepo extends MongoRepository<Candidate, String> {

	public Candidate findByName(String name);
	public List<Candidate> findByParty(String party);
	public List<Candidate> findAll();
	
}
