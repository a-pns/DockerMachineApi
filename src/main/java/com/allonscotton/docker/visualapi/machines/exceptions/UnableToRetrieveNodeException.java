package com.allonscotton.docker.visualapi.machines.exceptions;

import com.allonscotton.docker.visualapi.machines.StringConstants;

/**
 * Exception that is thrown when attempting to get a machine using the api but could not for some reason
 * @author allonscotton
 *
 */
public class UnableToRetrieveNodeException extends RuntimeException {

	private static final long serialVersionUID = 572278296375854653L;
	
	public UnableToRetrieveNodeException()
	{
		super(StringConstants.UNABLE_TO_RETRIEVE_NODE_INFO);
	}

}
