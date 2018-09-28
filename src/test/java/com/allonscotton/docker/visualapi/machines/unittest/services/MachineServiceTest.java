package com.allonscotton.docker.visualapi.machines.unittest.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.allonscotton.docker.visualapi.machines.exceptions.UnableToFindNodesException;
import com.allonscotton.docker.visualapi.machines.resources.Machine;
import com.allonscotton.docker.visualapi.machines.services.MachineService;
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerManagerStatus;
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerNode;
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerNodeStatus;
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerVersion;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.swarm.Node;

@RunWith(MockitoJUnitRunner.class)
public class MachineServiceTest {
	
	@Mock
	private DockerClient dockerClient;

	@Test
	public void testThatListMachinesReturnsEmptyList() throws DockerException, InterruptedException {
		MachineService machineService = new MachineService(dockerClient);
		
		when(dockerClient.listNodes()).thenReturn(new ArrayList<Node>());
		
		List<Machine> testResult = machineService.listMachines();
		Assert.assertNotNull(testResult);
		Assert.assertEquals(0, testResult.size());
		verify(dockerClient).listNodes();
	}
	
	@Test
	public void testThatListMachineReturnsOneItem() throws DockerException, InterruptedException {
		MachineService machineService = new MachineService(dockerClient);
		
		when(dockerClient.listNodes()).thenReturn(Arrays.asList(new TestDockerNode()));
		
		List<Machine> testResult = machineService.listMachines();
		Assert.assertNotNull(testResult);
		Assert.assertEquals(1, testResult.size());
		verify(dockerClient).listNodes();
			
	}
	
	@Test(expected = UnableToFindNodesException.class)
	public void TestThatUnableToFindNodesExceptionIsThrownWhenDockerExceptionIsThrown() throws DockerException, InterruptedException
	{
		MachineService machineService = new MachineService(dockerClient);
		
		when(dockerClient.listNodes()).thenThrow(DockerException.class);
		
		machineService.listMachines();
	}
	
	@Test(expected = UnableToFindNodesException.class)
	public void TestThatUnableToFindNodesExceptionIsThrownWhenInterruptedExceptionIsThrown() throws DockerException, InterruptedException
	{
		MachineService machineService = new MachineService(dockerClient);
		
		when(dockerClient.listNodes()).thenThrow(InterruptedException.class);
		
		machineService.listMachines();
	}
	
	@Test
	public void TestThatNodeIdIsMappedToMachineObjectsIDField() throws DockerException, InterruptedException
	{
		MachineService machineService = new MachineService(dockerClient);
		
		when(dockerClient.listNodes()).thenReturn(
				Arrays.asList(
						new TestDockerNode(
								"123456789", 
								new TestDockerVersion((long) 1), 
								new Date(), 
								new Date(), 
								null,
								null, new TestDockerNodeStatus("active", "192.0.0.1"), 
								new TestDockerManagerStatus(true, "up", "192.0.0.1")
								)
						)
				);
		
		List<Machine> testResult = machineService.listMachines();
		Assert.assertNotNull(testResult);
		Assert.assertEquals(1, testResult.size());
		Assert.assertEquals("123456789", testResult.get(0).getId());
		verify(dockerClient).listNodes();
	}

}
