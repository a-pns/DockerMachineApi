package com.allonscotton.docker.visualapi.machines.unittest.helpers;

import com.spotify.docker.client.messages.swarm.EngineConfig;
import com.spotify.docker.client.messages.swarm.NodeDescription;
import com.spotify.docker.client.messages.swarm.Platform;
import com.spotify.docker.client.messages.swarm.Resources;

public class TestDockerNodeDescription extends NodeDescription {
	
	public TestDockerNodeDescription()
	{
		
	}

	@Override
	public String hostname() {
		return null;
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
