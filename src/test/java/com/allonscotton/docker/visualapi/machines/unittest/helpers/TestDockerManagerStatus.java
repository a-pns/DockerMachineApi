package com.allonscotton.docker.visualapi.machines.unittest.helpers;

import com.spotify.docker.client.messages.swarm.ManagerStatus;

public class TestDockerManagerStatus extends ManagerStatus {
	
	private boolean leader;
	private String reachability;
	private String addr;
	
	public TestDockerManagerStatus(boolean leader, String reachability, String addr) {
		this.leader = leader;
		this.reachability = reachability;
		this.addr = addr;
	}

	@Override
	public Boolean leader() {
		return leader;
	}

	@Override
	public String reachability() {
		return reachability;
	}

	@Override
	public String addr() {
		return addr;
	}

}
