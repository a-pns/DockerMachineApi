package com.allonscotton.docker.visualapi.machines.exceptions;

import com.allonscotton.docker.visualapi.machines.StringConstants;

/**
 * ERxception that is thrown if a machine could not be found
 * @author allonscotton
 *
 */
public class MachineNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8853430212421873949L;
	
	public MachineNotFoundException()
	{
		super(StringConstants.MACHINE_NOT_FOUND);
	}
}
