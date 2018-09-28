package com.allonscotton.docker.visualapi.machines.machines.unittest.helpers;

import org.json.JSONObject;

public class JsonHelpers {
	
	public static boolean isValidJson(String json)
	{
		try {
			new JSONObject(json);
			
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}

}
