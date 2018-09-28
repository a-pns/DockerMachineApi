package com.allonscotton.docker.visualapi.machines.unittest.helpers;

import java.util.Date;

import com.spotify.docker.client.messages.swarm.ManagerStatus;
import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.NodeDescription;
import com.spotify.docker.client.messages.swarm.NodeSpec;
import com.spotify.docker.client.messages.swarm.NodeStatus;
import com.spotify.docker.client.messages.swarm.Version;

public class TestDockerNode extends Node {
	
	public TestDockerNode()
	{
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date createdAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date updatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeSpec spec() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeDescription description() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeStatus status() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagerStatus managerStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
