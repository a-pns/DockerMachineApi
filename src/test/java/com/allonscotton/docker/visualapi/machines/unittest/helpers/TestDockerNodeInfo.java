package com.allonscotton.docker.visualapi.machines.unittest.helpers;

import java.util.Date;

import com.spotify.docker.client.messages.swarm.ManagerStatus;
import com.spotify.docker.client.messages.swarm.NodeDescription;
import com.spotify.docker.client.messages.swarm.NodeInfo;
import com.spotify.docker.client.messages.swarm.NodeSpec;
import com.spotify.docker.client.messages.swarm.NodeStatus;
import com.spotify.docker.client.messages.swarm.Version;

public class TestDockerNodeInfo extends NodeInfo {
		
	private String id;
	private Version version;
	private Date created;
	private Date updated;
	private NodeSpec spec;
	private NodeDescription description;
	private NodeStatus status;
	private ManagerStatus managerStatus;
	
	public TestDockerNodeInfo()
	{
		this(null,null,null,null,null,null,null,null);
	}
	
	public TestDockerNodeInfo(String id,
			Version version,
			Date created,
			Date updated,
			NodeSpec spec,
			NodeDescription description,
			NodeStatus status,
			ManagerStatus managerStatus)
	{
		this.id = id;
		this.version = version;
		this.created = created;
		this.updated = updated;
		this.spec = spec;
		this.description = description;
		this.status = status;
		this.managerStatus = managerStatus;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public Version version() {
		return version;
	}

	@Override
	public Date createdAt() {
		return created;
	}

	@Override
	public Date updatedAt() {
		return updated;
	}

	@Override
	public NodeSpec spec() {
		return spec;
	}

	@Override
	public NodeDescription description() {
		return description;
	}

	@Override
	public NodeStatus status() {
		return status;
	}

	@Override
	public ManagerStatus managerStatus() {
		return managerStatus;
	}

}

