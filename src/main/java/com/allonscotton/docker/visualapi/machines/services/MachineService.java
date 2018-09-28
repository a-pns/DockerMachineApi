package com.allonscotton.docker.visualapi.machines.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allonscotton.docker.visualapi.machines.exceptions.UnableToFindNodesException;
import com.allonscotton.docker.visualapi.machines.resources.*;
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

	@Override
	public List<Machine> listMachines() {
		try {
			List<Machine> machines = new ArrayList<Machine>();
			List<Node> nodes = dockerClient.listNodes();
			machines = nodes.stream().map(
					node -> new Machine()
					).collect(Collectors.toList());
			return machines;
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			throw new UnableToFindNodesException();
		}
	}
}
