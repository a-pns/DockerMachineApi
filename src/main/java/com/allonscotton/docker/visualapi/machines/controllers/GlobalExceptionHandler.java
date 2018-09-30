package com.allonscotton.docker.visualapi.machines.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.allonscotton.docker.visualapi.machines.StringConstants;
import com.allonscotton.docker.visualapi.machines.exceptions.MachineNotFoundException;
import com.allonscotton.docker.visualapi.machines.exceptions.UnableToFindNodesException;
import com.allonscotton.docker.visualapi.machines.exceptions.UnableToRetrieveNodeException;
import com.allonscotton.docker.visualapi.machines.responses.ErrorResponse;

/**
 * Class that handles all the exceptions that come into the services and sends the appropriate response
 * @author allonscotton
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(UnableToFindNodesException.class)
    protected @ResponseBody ErrorResponse handleUnableToFindNodesException(UnableToFindNodesException ex) {
        return new ErrorResponse(500, ex.getMessage());
    }
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
    protected @ResponseBody ErrorResponse handleAnyUncaughtExceptions(Exception ex) {
        return new ErrorResponse(500, StringConstants.GENERIC_INTERNAL_SERVER_ERROR);
    }
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ExceptionHandler(MachineNotFoundException.class)
    protected @ResponseBody ErrorResponse handleMachineNotFoundException(MachineNotFoundException ex) {
        return new ErrorResponse(404, ex.getMessage());
    }
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(UnableToRetrieveNodeException.class)
    protected @ResponseBody ErrorResponse handleUnableToRetrieveNodeException(UnableToRetrieveNodeException ex) {
        return new ErrorResponse(500, ex.getMessage());
    }
}