package com.allonscotton.docker.visualapi.machines.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allonscotton.docker.visualapi.machines.exceptions.UnableToFindNodesException;
import com.allonscotton.docker.visualapi.machines.resources.*;
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerNode;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.swarm.Node;

@Service
public class MachineService implements MachineServiceI {
	
	private DockerClient dockerClient;
	
	@Autowired
	public MachineService(DockerClient dockerClient) {
		this.dockerClient = dockerClient;
	}

	/**
	 * Returns the list of machines that are in the docker swarm
	 */
	@Override
	public List<Machine> listMachines() {
		try {
			List<Machine> machines = new ArrayList<Machine>();
			List<Node> nodes = dockerClient.listNodes();
			machines = nodes.stream().map(
					node -> mapMachine(node)
					).collect(Collectors.toList());
			return machines;
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			throw new UnableToFindNodesException();
		}
	}

	/**
	 * Maps a Node object to a Machine object
	 * @param node
	 * @return
	 */
	public Machine mapMachine(Node node) {
		long version = (long) -1;
		String hostname = "Unknown";
		String ip = "Unknown";
		String status = "Unknown";

		if (node.version() != null)
			version = node.version().index();
		if (node.description() != null)
			hostname = node.description().hostname();
		if (node.status() != null)
		{
			ip = node.status().addr();
			status = node.status().state();
		}
		Machine theMachine = new Machine(
				node.id(), 
				node.createdAt(), 
				node.updatedAt(), 
				version, 
				hostname,
				ip,
				status);
		
		return theMachine;
	}
}
