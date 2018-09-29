package com.allonscotton.docker.visualapi.machines.resources;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.text.DateFormat;

public class Machine {
	private String id;
	private Date created;
	private Date updated;
	private long version;
	private String hostname;
	private String ip;
	private String status;
	
	public Machine(String id, Date created, Date updated, Long version, String hostname, String ip, String status)
	{
		this.id = id;
		this.created = created;
		this.updated = updated;
		this.version = version;
		this.hostname = hostname;
		this.ip = ip;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
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
