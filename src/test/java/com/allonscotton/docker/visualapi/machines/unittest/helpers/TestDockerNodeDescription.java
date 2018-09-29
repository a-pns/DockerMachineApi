package com.allonscotton.docker.visualapi.machines.unittest.helpers;

import com.spotify.docker.client.messages.swarm.EngineConfig;
import com.spotify.docker.client.messages.swarm.NodeDescription;
import com.spotify.docker.client.messages.swarm.Platform;
import com.spotify.docker.client.messages.swarm.Resources;

public class TestDockerNodeDescription extends NodeDescription {
	
	private String hostname;
	
	public TestDockerNodeDescription(String hostname)
	{
		this.hostname = hostname;
	}

	@Override
	public String hostname() {
		return hostname;
	}

	@Override
	public Platform platform() {
		return null;
	}

	@Override
	public Resources resources() {
		return null;
	}

	@Override
	public EngineConfig engine() {
		return null;
	}

}
