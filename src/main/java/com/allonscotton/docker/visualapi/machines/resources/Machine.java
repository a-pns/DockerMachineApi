package com.allonscotton.docker.visualapi.machines.resources;

public class Machine {
	private String id;
	
	public Machine(String id)
	{
		this.setId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
