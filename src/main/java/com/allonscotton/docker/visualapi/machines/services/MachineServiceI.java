package com.allonscotton.docker.visualapi.machines.services;

import java.util.List;

import com.allonscotton.docker.visualapi.machines.resources.*;

/**
 * Interface for the docker machine services to reduce coupling and increase cohesion of the service classes
 * @author allonscotton
 *
 */
public interface MachineServiceI {
	public List<Machine> listMachines();
}
