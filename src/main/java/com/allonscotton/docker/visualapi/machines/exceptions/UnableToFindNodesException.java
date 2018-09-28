package com.allonscotton.docker.visualapi.machines.exceptions;

import com.allonscotton.docker.visualapi.machines.StringConstants;

public class UnableToFindNodesException extends RuntimeException {

	private static final long serialVersionUID = -4906345206653800329L;
	
	public UnableToFindNodesException()
	{
		super(StringConstants.UNABLE_TO_LIST_NODES_EXCEPTION);
	}

}
