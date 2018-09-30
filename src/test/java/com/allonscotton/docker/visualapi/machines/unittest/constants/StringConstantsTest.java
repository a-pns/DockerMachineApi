package com.allonscotton.docker.visualapi.machines.unittest.constants;

import org.junit.Assert;
import org.junit.Test;

import com.allonscotton.docker.visualapi.machines.StringConstants;

public class StringConstantsTest {
	
	@Test
	public void testThatUnableToFindMachinesMessageIsAsExpected()
	{
		Assert.assertEquals("Unable to retrive a list of machines", StringConstants.UNABLE_TO_LIST_NODES_EXCEPTION);
	}
	@Test
	public void testThatGenericInternalServerErrorMessageIsAsExpected()
	{
		Assert.assertEquals("Whoops! looks like an unexpected error has occurred", StringConstants.GENERIC_INTERNAL_SERVER_ERROR);
	}
	@Test
	public void testThatUnableToRetrieveNodeInfoMessageIsAsExpected()
	{
		Assert.assertEquals("Unable to retrive info about this machine", StringConstants.UNABLE_TO_RETRIEVE_NODE_INFO);
	}	@Test
	public void testThatMachineNotFoundMessageIsAsExpected()
	{
		Assert.assertEquals("Looks like this machine could not be found", StringConstants.MACHINE_NOT_FOUND);
	}
}
