package com.allonscotton.docker.visualapi.machines.unittest.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.allonscotton.docker.visualapi.machines.unittest.helpers.TestDockerNodeDescription;
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
	
	@Test
	public void testThatMapNodeToMachineReturnsMachine()
	{
		MachineService machineService = new MachineService(dockerClient);
		
		TestDockerNode node = new TestDockerNode();
		
		Machine machine = machineService.mapMachine(node);
		
		Assert.assertNotNull(machine);
	}
	
	@Test
	public void testThatMapNodeToMachineSetsFieldsCorrectly()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 8, 29, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date created = cal.getTime();
		cal.set(2018, 9, 1, 23, 59, 0);
		Date updated = cal.getTime();
		TestDockerVersion dockerVersion = new TestDockerVersion((long) 1); 
		TestDockerNodeDescription nodeDescription = new TestDockerNodeDescription("localhost");
		TestDockerNodeStatus nodeStatus = new TestDockerNodeStatus("UP", "127.7.7.7");
		TestDockerManagerStatus managerStatus = new TestDockerManagerStatus(true, "UP", "127.7.7.7");
		
		MachineService machineService = new MachineService(dockerClient);
		
		TestDockerNode node = new TestDockerNode(
				"TheId", 
				dockerVersion,
				created,
				updated,
				null,
				nodeDescription,
				nodeStatus,
				managerStatus
				);
		
		Machine machine = machineService.mapMachine(node);
		
		Assert.assertNotNull(machine);
		Assert.assertEquals("29-09-2018 00:00:00.000", machine.getCreated());
		Assert.assertEquals("01-10-2018 23:59:00.000", machine.getUpdated());
		Assert.assertEquals(dockerVersion.index(), machine.getVersion());
		Assert.assertEquals(nodeDescription.hostname(), machine.getHostname());
		Assert.assertEquals(nodeStatus.addr(), machine.getIP());
		Assert.assertEquals(nodeStatus.state(), machine.getStatus());
	}
	
	@Test
	public void testThatMapNodeToMachineHandlesNullVersion()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 9, 29);
		Date created = cal.getTime();
		cal.set(2018, 10, 1, 23, 59);
		Date updated = cal.getTime();
		TestDockerVersion dockerVersion = null;
		TestDockerNodeDescription nodeDescription = new TestDockerNodeDescription("localhost");
		TestDockerNodeStatus nodeStatus = new TestDockerNodeStatus("UP", "127.7.7.7");
		TestDockerManagerStatus managerStatus = new TestDockerManagerStatus(true, "UP", "127.7.7.7");
		
		MachineService machineService = new MachineService(dockerClient);
		
		TestDockerNode node = new TestDockerNode(
				"TheId", 
				dockerVersion,
				created,
				updated,
				null,
				nodeDescription,
				nodeStatus,
				managerStatus
				);
		
		Machine machine = machineService.mapMachine(node);
		
		Assert.assertNotNull(machine);
		Assert.assertEquals( (long) -1, (long) machine.getVersion());
	}
	
	@Test
	public void testThatMapNodeToMachineHandlesNullDescription()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 9, 29);
		Date created = cal.getTime();
		cal.set(2018, 10, 1, 23, 59);
		Date updated = cal.getTime();
		TestDockerVersion dockerVersion = new TestDockerVersion((long) 1); 
		TestDockerNodeDescription nodeDescription = null;
		TestDockerNodeStatus nodeStatus = new TestDockerNodeStatus("UP", "127.7.7.7");
		TestDockerManagerStatus managerStatus = new TestDockerManagerStatus(true, "UP", "127.7.7.7");
		
		MachineService machineService = new MachineService(dockerClient);
		
		TestDockerNode node = new TestDockerNode(
				"TheId", 
				dockerVersion,
				created,
				updated,
				null,
				nodeDescription,
				nodeStatus,
				managerStatus
				);
		
		Machine machine = machineService.mapMachine(node);
		
		Assert.assertNotNull(machine);
		Assert.assertEquals( "Unknown" , machine.getHostname());
	}
	
	@Test
	public void testThatMapNodeToMachineHandlesNullStatus()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 9, 29);
		Date created = cal.getTime();
		cal.set(2018, 10, 1, 23, 59);
		Date updated = cal.getTime();
		TestDockerVersion dockerVersion = new TestDockerVersion((long) 1); 
		TestDockerNodeDescription nodeDescription = new TestDockerNodeDescription("localhost");
		TestDockerNodeStatus nodeStatus = null;
		TestDockerManagerStatus managerStatus = new TestDockerManagerStatus(true, "UP", "127.7.7.7");
		
		MachineService machineService = new MachineService(dockerClient);
		
		TestDockerNode node = new TestDockerNode(
				"TheId", 
				dockerVersion,
				created,
				updated,
				null,
				nodeDescription,
				nodeStatus,
				managerStatus
				);
		
		Machine machine = machineService.mapMachine(node);
		
		Assert.assertNotNull(machine);
		Assert.assertEquals( "Unknown" , machine.getStatus());
		Assert.assertEquals( "Unknown" , machine.getIP());
	}

}
