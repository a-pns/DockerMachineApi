package com.allonscotton.docker.visualapi.machines.unittest.controllers;

import java.util.ArrayList;
import java.util.Arrays;

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
		
		when(machineService.listMachines()).thenReturn(Arrays.asList(new Machine("987456321")));
		
		Resources<Resource<Machine>> machines = machineController.listAllMachines();
		Assert.assertNotNull(machines);
		Assert.assertEquals(1,machines.getContent().size());
	}
}
