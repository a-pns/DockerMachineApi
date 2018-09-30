package com.allonscotton.docker.visualapi.machines.unittest.helpers;

import java.util.Date;


public final class TestDataHelper {
	
	public static TestDockerNode getTestDockerNode(String id, long version, String nodeStatus, String managerStatus, String ip, String hostname, boolean isManager, Date created, Date updated)
	{
		return new TestDockerNode(
				id, 
				new TestDockerVersion(version), 
				created, 
				updated, 
				null,
				new TestDockerNodeDescription(hostname), 
				new TestDockerNodeStatus(nodeStatus, ip), 
				new TestDockerManagerStatus(isManager, managerStatus, ip));
	}
	
	public static TestDockerNodeInfo getTestDockerNodeInfo(String id, long version, String nodeStatus, String managerStatus, String ip, String hostname, boolean isManager, Date created, Date updated)
	{
		return new TestDockerNodeInfo(
				id, 
				new TestDockerVersion(version), 
				created, 
				updated, 
				null,
				new TestDockerNodeDescription(hostname), 
				new TestDockerNodeStatus(nodeStatus, ip), 
				new TestDockerManagerStatus(isManager, managerStatus, ip));
	}

}
