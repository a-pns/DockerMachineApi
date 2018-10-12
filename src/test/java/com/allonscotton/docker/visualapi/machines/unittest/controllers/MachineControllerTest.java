package com.allonscotton.docker.visualapi.machines.unittest.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import static org.mockito.Mockito.*;

import com.allonscotton.docker.visualapi.machines.controllers.MachineController;
import com.allonscotton.docker.visualapi.machines.resources.Machine;
import com.allonscotton.docker.visualapi.machines.services.MachineService;

@RunWith(MockitoJUnitRunner.class)
public class MachineControllerTest {
	
	@Mock
	private MachineService machineService;
	
	@Before
	public void setup()
	{
		// TODO does nothing yet
	}

	@Test
	public void testThatGetEndPointReturnsEmptyListOfMachineResources() {
		MachineController machineController = new MachineController(machineService);
		
		when(machineService.listMachines()).thenReturn(new ArrayList<Machine>());
		
		Resources<Resource<Machine>> machines = machineController.listAllMachines();
		Assert.assertNotNull(machines);
		Assert.assertEquals(0,machines.getContent().size());
	}
	
	@Test
	public void testThatGetEndPointReturnsTheCorrectLinksMetadata() {
		MachineController machineController = new MachineController(machineService);
		
		when(machineService.listMachines()).thenReturn(new ArrayList<Machine>());
		
		Resources<Resource<Machine>> machines = machineController.listAllMachines();
		Assert.assertNotNull(machines);
		Assert.assertEquals(0,machines.getContent().size());
		Assert.assertEquals("/machine/", machines.getLink("self").getHref());
		Assert.assertEquals("self", machines.getLink("self").getRel());
	}
	
	@Test
	public void testThatGetEndPointReturnsOneMachine() {
		MachineController machineController = new MachineController(machineService);
		Machine machine = new Machine("987456321", new Date(), new Date(), (long) 1, null, null, null);
		
		when(machineService.listMachines()).thenReturn(Arrays.asList(machine));
		
		Resources<Resource<Machine>> machines = machineController.listAllMachines();
		Assert.assertNotNull(machines);
		Assert.assertEquals(1,machines.getContent().size());
	}
	
	@Test
	public void testThatGetMachineEndPointReturnsMachineResource() {
		MachineController machineController = new MachineController(machineService);
		Machine mockMachine = new Machine("abc123", null, null, (long) 123, null, null, null);
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(machineService.getMachine(anyString())).thenReturn(mockMachine);
		when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/machine/abc123"));

		
		Resource<Machine> machine = machineController.getMachine("abc123", mockRequest);
		Assert.assertNotNull(machine);
		Assert.assertNotNull(machine.getContent());
	}
	
	@Test
	public void testThatGetMachineEndPointSetAppropriateLinks()
	{
		MachineController machineController = new MachineController(machineService);
		Machine mockMachine = new Machine("abc123", null, null, (long) 123, null, null, null);
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);

		when(machineService.getMachine(anyString())).thenReturn(mockMachine);
		when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/machine/abc123"));
		
		Resource<Machine> machine = machineController.getMachine("abc123", mockRequest);
		Assert.assertNotNull(machine);
		Assert.assertEquals("/machine/abc123", machine.getLink("self").getHref());
		Assert.assertEquals("self", machine.getLink("self").getRel());
		Assert.assertEquals("/machine/", machine.getLink("machine.list").getHref());
		Assert.assertEquals("machine.list", machine.getLink("machine.list").getRel());
		Assert.assertEquals("http://localhost/machine/abc123/container", machine.getLink("machine.container").getHref());
		Assert.assertEquals("machine.container", machine.getLink("machine.container").getRel());
	}
}
