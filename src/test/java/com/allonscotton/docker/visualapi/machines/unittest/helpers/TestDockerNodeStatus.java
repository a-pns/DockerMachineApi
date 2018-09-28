package com.allonscotton.docker.visualapi.machines.unittest.helpers;

import com.spotify.docker.client.messages.swarm.NodeStatus;

public class TestDockerNodeStatus extends NodeStatus {

	private String state;
	private String addr;
	
	public TestDockerNodeStatus(String state, String addr)
	{
		this.state = state;
		this.addr = addr;
	}
	
	@Override
	public String state() {
		return state;
	}

	@Override
	public String addr() {
		return addr;
	}

}
