package com.issinc.mark;

public class Candidate {
	
	private String id;
	private String name;
	private String party;
	private int delegates;
	
	public Candidate(){}
	
	public Candidate(String name, String party, int delegates){
		this.name = name;
		this.party = party;
		this.delegates = delegates;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public int getDelegates() {
		return delegates;
	}
	public void setDelegates(int delegates) {
		this.delegates = delegates;
	}
	
	@Override
	public String toString() {
		return String.format("Candidate[id=%s, name=%s, party=%s, delegates=%s]", 
				id, name, party, delegates);
	}

}
