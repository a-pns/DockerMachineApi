package com.allonscotton.docker.visualapi.machines.unittest.helpers;

import com.spotify.docker.client.messages.swarm.Version;

public class TestDockerVersion extends Version {
	
	private long index;
	
	public TestDockerVersion(long index) {
		this.index = index;
	}

	@Override
	public Long index() {
		return index;
	}

}
