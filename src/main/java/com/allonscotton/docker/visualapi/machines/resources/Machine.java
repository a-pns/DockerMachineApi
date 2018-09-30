package com.allonscotton.docker.visualapi.machines.resources;

import java.util.Date;

import java.text.SimpleDateFormat;

/**
 * Class that models the Docker node on a swarm
 * Has fields for all the pertinent information returned by the API
 * @author allonscotton
 *
 */
public class Machine {
	private String id;
	private Date created;
	private Date updated;
	private long version;
	private String hostname;
	private String ip;
	private String status;
	private SimpleDateFormat format;
	
	public Machine(String id, Date created, Date updated, Long version, String hostname, String ip, String status)
	{
		this.id = id;
		this.created = created;
		this.updated = updated;
		this.version = version;
		this.hostname = hostname;
		this.ip = ip;
		this.status = status;
		format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		
	}

	public String getId() {
		return id;
	}

	public String getCreated() {
		return format.format(created);
	}

	public String getUpdated() {
		return format.format(updated);
	}

	public Long getVersion() {
		return version;
	}

	public String getHostname() {
		return hostname;
	}

	public String getIP() {
		return ip;
	}

	public String getStatus() {
		return status;
	}
}
