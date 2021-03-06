package com.allonscotton.docker.visualapi.machines.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.allonscotton.docker.visualapi.machines.resources.Machine;
import com.allonscotton.docker.visualapi.machines.services.MachineServiceI;

/**
 * Handles the REST request that come into the service and sends the appropriate responses
 * @author allonscotton
 *
 */
@RestController
@RequestMapping("machine")
public class MachineController {
	
	private MachineServiceI machineService;
	
	@Autowired
	public MachineController(MachineServiceI machineService)
	{
		this.machineService = machineService;
	}

	/**
	 * This method returns all the docker machines that are running in the hosts docker swarm
	 * @return
	 */
	@GetMapping(path = "", produces = "application/json")
	public Resources<Resource<Machine>> listAllMachines() {
		List<Machine> machines = machineService.listMachines();
		
		// Convert these machines into HATEOS format
		List<Resource<Machine>> resources = machines.stream().map(
				machine -> new Resource<Machine>(machine)
				).collect(Collectors.toList());
		
		// Add the self referencing link to the HATEOS resource
		Resources<Resource<Machine>> result = new Resources<Resource<Machine>>(
				resources, 
				linkTo(methodOn(MachineController.class).listAllMachines()).withSelfRel()
				);
		
		return result;
	}

	@GetMapping(path = "/{id}")
	public Resource<Machine> getMachine(@PathVariable(name = "id") String id, HttpServletRequest request) {
		Machine machine = machineService.getMachine(id);
		Link containerLink = new Link(request.getRequestURL().toString() + "/container","machine.container");
		Resource<Machine> resource = new Resource<Machine>(machine, 
				linkTo(methodOn(MachineController.class).getMachine(id, request)).withSelfRel(),
				linkTo(methodOn(MachineController.class).listAllMachines()).withRel("machine.list"),
				containerLink);
		return resource;
	}
	
}
